package com.sap.appsexperience;

import com.sap.appsexperience.model.Usuario;
import com.sap.appsexperience.model.Combustivel.ETipo;
import com.sap.appsexperience.model.dao.UsuarioDAO;
import com.sap.appsexperience.util.LocalCache;
import com.sap.appsexperience.util.MenuHandler;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class OndeAbastecerActivity extends TabActivity {
	
	ProgressDialog dialog;
	protected Dialog splashDialog;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        LocalCache.initInstance(getApplicationContext());
        LocalCache.getInstance().setLatitude(getIntent().getExtras().getDouble("latitude"));
        LocalCache.getInstance().setLongitude(getIntent().getExtras().getDouble("longitude"));
        
        LinearLayout layoutSetup = (LinearLayout)findViewById(R.id.layoutIniSetup);
    	LinearLayout layoutMain = (LinearLayout)findViewById(R.id.layoutMainTabs);
        
    	    	
    	if(LocalCache.getInstance().getUsuarioAtual() == null) {
        	layoutSetup.setVisibility(View.VISIBLE);
        	layoutMain.setVisibility(View.GONE);
        } else {
        	inicializarTabs();
        }
        
    }
    
    private void inicializarTabs() {
    	
    	LinearLayout layoutSetup = (LinearLayout)findViewById(R.id.layoutIniSetup);
    	LinearLayout layoutMain = (LinearLayout)findViewById(R.id.layoutMainTabs);
    	
    	layoutSetup.setVisibility(View.GONE);
    	layoutMain.setVisibility(View.VISIBLE);
    	
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	EditText txtApelido = (EditText)findViewById(R.id.txtApelidoCadastro);
    	imm.hideSoftInputFromWindow(txtApelido.getWindowToken(), 0);
    	
    	try {
        	
    		Resources res = getResources();
        	TabHost tabHost = getTabHost();
			TabSpec tabStations = tabHost.newTabSpec("stations");
			TabSpec tabMap = tabHost.newTabSpec("map"); 
			TabSpec tabFavStations = tabHost.newTabSpec("favStations");
			
			Intent stationsActivity = new Intent(this, StationsActivity.class);
			stationsActivity.putExtra("listaFavoritos", false);
			tabStations.setIndicator("Postos", res.getDrawable(R.drawable.tb_postos)).setContent(stationsActivity);
			
			
			tabMap.setIndicator("Mapa", res.getDrawable(R.drawable.tb_mapa)).setContent(new Intent(this,MapActivityTab.class));
			
			Intent favStationsActivity = new Intent(this, StationsActivity.class);
			favStationsActivity.putExtra("listaFavoritos", true);
			tabFavStations.setIndicator("Favoritos", res.getDrawable(R.drawable.tb_favoritos)).setContent(favStationsActivity);
			
			tabHost.addTab(tabMap);
			tabHost.addTab(tabStations);
			tabHost.addTab(tabFavStations);
			
			tabHost.setCurrentTab(1);
			setupTabs();
			
    	}
        catch (Exception e) {
        	System.out.println(">>>OndeAbastecerActivity.onCreate(). " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	private void setupTabs() {
		/*
		RadioButton rbTab1 = (RadioButton) findViewById(R.id.tab1);
	    RadioButton rbTab2 = (RadioButton) findViewById(R.id.tab2);
	    RadioButton rbTab3 = (RadioButton) findViewById(R.id.tab3);
	    rbTab1.setButtonDrawable(R.drawable.tb_mapa);
	    rbTab2.setButtonDrawable(R.drawable.tb_postos);
	    rbTab3.setButtonDrawable(R.drawable.tb_favoritos);

	    RadioGroup rg = (RadioGroup) findViewById(R.id.rdTabs);
	    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
	        public void onCheckedChanged(RadioGroup group, final int checkedId) {
	            switch (checkedId) {
	            case R.id.tab1:
	            	getTabHost().setCurrentTab(0);
	                break;
	            case R.id.tab2:
	            	getTabHost().setCurrentTab(1);
	                break;
	            case R.id.tab3:
	            	getTabHost().setCurrentTab(2);
	            	break;
	            }
	        }
	    });*/
	}

	public void novoUsuario(View view) {
		Button btnCadastrar = (Button)findViewById(R.id.btnNovoUsuario);
		btnCadastrar.setEnabled(false);
		TextView txtErro = (TextView)findViewById(R.id.txtErroCadastro);
		txtErro.setVisibility(View.GONE);
		
		dialog = ProgressDialog.show(OndeAbastecerActivity.this, "", "Verificando apelido...", true);
    	
		new Thread(new Runnable() { // passar itens de UI para POST
		    public void run() {
		    	
		    	EditText txtApelido = (EditText)findViewById(R.id.txtApelidoCadastro);
		    	Usuario novoUsuario = UsuarioDAO.dao().novaInstalacao(txtApelido.getText().toString());
		    	
		    	if(novoUsuario == null) { // j� existe, mostrar erro
		    		TextView txtErro = (TextView)findViewById(R.id.txtErroCadastro);
		    		txtErro.post(new Runnable() {
						
						
						public void run() {
							TextView txtErro = (TextView)findViewById(R.id.txtErroCadastro);
							txtErro.setVisibility(View.VISIBLE);
						}
					});
		    		
		    	} else { // pode continuar
		    		LocalCache.getInstance().setUsuarioAtual(novoUsuario);
		    		LocalCache.getInstance().updateUsuarioAtual();
		    		runOnUiThread(new Runnable() {
						
						
						public void run() {
							inicializarTabs();
							
						}
					});
		    		
		    	}
		    	dialog.dismiss();
		    	
		    	Button btnCadastrar = (Button)findViewById(R.id.btnNovoUsuario);
				btnCadastrar.post(new Runnable() {
					
					
					public void run() {
						Button btnCadastrar = (Button)findViewById(R.id.btnNovoUsuario);
						btnCadastrar.setEnabled(true);
					}
				});
		    	
		    }
		  }).start();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return MenuHandler.onCreateOptionsMenu(getMenuInflater(), menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	if(MenuHandler.onOptionsItemSelected(item, this)) {
    		return true;
    	} else {
    		return super.onOptionsItemSelected(item);
    	}
    }

    
}