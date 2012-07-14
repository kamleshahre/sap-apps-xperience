package com.sap.appsexperience;

import java.io.IOException;
import java.util.List;

import android.R.style;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ZoomControls;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.sap.appsexperience.model.Combustivel;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.Combustivel.ETipo;
import com.sap.appsexperience.model.Posto.EBandeira;
import com.sap.appsexperience.util.LocalCache;


public class MapActivityTab extends MapActivity {

	private MapView mapView;
	private MapController mapController;
	private LocalCache localCache;
	private GeoPoint p;
	private ZoomControls zoomControls;
	int zoomLevel = 13;


	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.map);
		localCache = LocalCache.getInstance();		
		setMapController();		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		populateMap();
		centerMap();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setMapController() {

		try {
			mapView = (MapView) findViewById(R.id.mapView);
			mapController = mapView.getController();
			mapController.setZoom(zoomLevel);
			
			zoomControls = (ZoomControls)findViewById(R.id.zoomControls);
			
			zoomControls.setOnZoomInClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					mapController.setZoom(++zoomLevel);
					
				}
			});
			
			zoomControls.setOnZoomOutClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					mapController.setZoom(--zoomLevel);
					
				}
			});
			
		} catch (Exception e) {
			System.out
					.println(">>> OndeAbastecerActivity.setMapController() - "
							+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void processLocation(Location location) {

		
		try {
			
		} catch (Exception e) {
			System.out
			.println(">>> OndeAbastecerActivity.processLocation() - "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	private void populateMap() {
		if(localCache.getPostos().size() > 0) {
			mapView.getOverlays().clear();
			List<Overlay> overlays = mapView.getOverlays();

			MapOverlay userOverlay = new MapOverlay();
			userOverlay.setLatitude(localCache.getLatitude());
			userOverlay.setLongitude(localCache.getLongitude());
			userOverlay.setBandeira("pn_user");
			overlays.add(userOverlay);
			
			for (Posto p : localCache.getPostos()) {
				MapOverlay overlay = new MapOverlay();
				overlay.setLatitude(p.getLatitude());
				overlay.setLongitude(p.getLongitude());
				String uri = p.getBandeira().getFigura().replace(".png", "").replace("ic", "pn");
				overlay.setBandeira(uri);
				overlay.setPosto(p);
				overlay.setActivity(this);
				if(localCache.getPostos().indexOf(p) == 0) {
					overlay.setMaisBarato(true);
				}
				else
					overlay.setMaisBarato(false);
				
				try {
					if(localCache.getTipoPreferencia() == ETipo.gasolina) {
						overlay.setPrice(String.valueOf(p.getCombustivelByTipo(ETipo.gasolina).getPrecoAtual().getPreco()));
					}
					else if (localCache.getTipoPreferencia() == ETipo.alcool) {
						overlay.setPrice(String.valueOf(p.getCombustivelByTipo(ETipo.alcool).getPrecoAtual().getPreco()));
					} 
					else {
						overlay.setPrice(String.valueOf(p.getCombustivelByTipo(ETipo.diesel).getPrecoAtual().getPreco()));
					}
				} catch (Exception e) {
					overlay.setPrice("null");
					e.printStackTrace();
				}
			
				overlays.add(overlay);
			}
		}
	}
	
	private void centerMap() {
		if(localCache.getLatitude() != 0 && localCache.getLongitude() != 0) {
			mapController.animateTo(new GeoPoint( 	(int) (localCache.getLatitude() * 1000000), 
													(int) (localCache.getLongitude() * 1000000)));
			mapController.setZoom(13);
		}		
	}

	class MapOverlay extends com.google.android.maps.Overlay
    {
		private double latitude;
		private double longitude; 
		private String bandeira;	
		private String price;
		private boolean isMaisBarato = false;
		private Posto posto;
		private MapActivity activity;
		private Bitmap bitmap;
		
		
        
		/*@Override
		 * public boolean onTap(GeoPoint p, MapView mapView) {
        	if(!this.bandeira.contains("user")) {
        		Intent alertasActivity = new Intent(activity.getApplicationContext(),
        				DetalhePostoActivity.class);
        		alertasActivity.putExtra("posto", posto);
        		activity.startActivity(alertasActivity);
        	}        	
			return true;
		}*/
		@Override
        public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) 
        {        	
            super.draw(canvas, mapView, shadow);                   
            GeoPoint p = new GeoPoint( 	(int) (latitude * 1000000), 
													(int) (longitude * 1000000));
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(p, screenPts);
 
            String imgFilePath = "imagens/" + bandeira + ".png";
            
            
            Paint pricePaint = new Paint();
            
            if(!isMaisBarato) {	            
	            pricePaint.setColor(Color.BLUE); 
	            pricePaint.setTextSize(14);
            }
            else {
            	pricePaint.setColor(Color.MAGENTA);
            	pricePaint.setTextSize(17);
            	pricePaint.setFakeBoldText(true);
            }
            //pricePaint.setFakeBoldText(true);
           
           
            try {
    			bitmap = BitmapFactory.decodeStream(getResources().getAssets().open(imgFilePath));
    			
    			canvas.drawBitmap(bitmap, screenPts.x, screenPts.y-32, null);    	
    			if(price != null && price != "null") {
    				canvas.drawText("R$ " + price, screenPts.x + 34, screenPts.y - 16, pricePaint);
    			}
    			

    		} catch (IOException e) {
    			try {
    				bitmap = BitmapFactory.decodeStream(getResources().getAssets()
    								.open("imagens/pn_branco.png"));
    				canvas.drawBitmap(bitmap, screenPts.x, screenPts.y-32, null);  
    				if(price != null && price != "null") {
        				canvas.drawText("R$ " + price, screenPts.x + 34, screenPts.y - 16, pricePaint);
        			}
    			} catch (IOException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    		}
            
            
            
            return true;
        }
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
		public String getBandeira() {
			return bandeira;
		}
		public void setBandeira(String bandeira) {
			this.bandeira = bandeira;
		}
		
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public boolean isMaisBarato() {
			return isMaisBarato;
		}
		public void setMaisBarato(boolean isMaisBarato) {
			this.isMaisBarato = isMaisBarato;
		}
		public Posto getPosto() {
			return posto;
		}
		public void setPosto(Posto posto) {
			this.posto = posto;
		}
		public MapActivity getActivity() {
			return activity;
		}
		public void setActivity(MapActivity activity) {
			this.activity = activity;
		}
    } 

	
}
