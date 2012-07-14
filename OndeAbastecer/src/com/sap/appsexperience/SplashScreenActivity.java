package com.sap.appsexperience;


import com.sap.appsexperience.util.AppUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashScreenActivity extends Activity {
	/**
	 * The thread to process splash screen events
	 */
	private Thread mSplashThread;
	private LocationManager locationManager;
	private LocationListener locationListener;
	final SplashScreenActivity sPlashScreen = this;
	
	private int counter;
	private boolean mpPlayer = false;
	MediaPlayer mp;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash);
		
		if(!isNetworkAvailable()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Este programa necessita conectividade, ative seu Wi-fi ou 3G")
			       .setCancelable(false)
			       .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   SplashScreenActivity.this.finish();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			startLocationListener();
		}
		//mSplashThread.start();
	}
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    
	    if(activeNetworkInfo == null) {
	    	return false;
	    } else {
	    	String response = AppUtil.httpGet("http://www.google.com");
	    	if(response.isEmpty())
	    		return false;
	    }
	    
	    return true;
	}

	private void startLocationListener() {

		try {
			// Acquire a reference to the system Location Manager
			locationManager = (LocationManager) this
					.getSystemService(Context.LOCATION_SERVICE);

			// Define a listener that responds to the location updates
			locationListener = new LocationListener() {

				public void onLocationChanged(Location location) {
					//processLocation(location);
					locationManager.removeUpdates(locationListener);
					
					// Run next activity
					Intent intent = new Intent();
					intent.setClass(sPlashScreen, OndeAbastecerActivity.class);
					intent.putExtra("latitude", location.getLatitude());
					intent.putExtra("longitude", location.getLongitude());
					finish();
					startActivity(intent);
					

				}

				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub

				}

				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub

				}

				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub

				}

			};

			locationManager
					.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
							2000, 0, locationListener);
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 2000, 0, locationListener);
		} catch (Exception e) {
			System.out
					.println(">>> OndeAbastecerActivity.setLocationListener() - "
							+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void processLocation(Location location) {

		try {
			//localCache.setLatitude(location.getLatitude());
			//localCache.setLongitude(location.getLongitude());
			locationManager.removeUpdates(locationListener);

		} catch (Exception e) {
			System.out.println(">>> OndeAbastecerActivity.processLocation() - "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Processes splash screen touch events
	 */
	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		if (evt.getAction() == MotionEvent.ACTION_DOWN) {
			/*
			synchronized (mSplashThread) {
				mSplashThread.notifyAll();
			}*/
			if(!mpPlayer) {
				counter++;
				if(counter >= 3) {
					mp = MediaPlayer.create(SplashScreenActivity.this, R.raw.shoryuken);   
	                mp.start();
	                /*
	                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
	
	                    @Override
	                    public void onCompletion(MediaPlayer mp) {
	                        // TODO Auto-generated method stub
	                        mp.release();
	                    }
	
	                });*/
	                mpPlayer = true;
				}
			}
		}
		return true;
	}
}
