package com.sap.appsexperience;

import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sap.appsexperience.model.Combustivel;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.PostoComparator;
import com.sap.appsexperience.model.dao.PostoDAO;
import com.sap.appsexperience.util.LocalCache;

public class StationsActivity extends ListActivity {
	private List<Posto> listaPostos = null;
	private double ultimoRaio = 0;
	private Combustivel.ETipo ultimoTipo = Combustivel.ETipo.gasolina;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		atualizarLista();
	}
	
    public void onResume() {
    	super.onResume();
    	atualizarLista();
    }

    public void atualizarLista() {
    	new Thread(new Runnable() {
			public void run() {
				LocalCache cache = LocalCache.getInstance();
				boolean alterarAdapter = false;
				Object extraListaFavoritos = getIntent().getExtras().get(
						"listaFavoritos");
				boolean listaFavoritos = (extraListaFavoritos == null ? false
						: ((Boolean) extraListaFavoritos));

				if (listaFavoritos) {
					listaPostos = PostoDAO.dao().getPostosById(
							cache.getLatitude(),
							cache.getLongitude(),
							LocalCache.getInstance().getUsuarioAtual()
									.getFavoritosAsString());
					alterarAdapter = true;
				} else {
					double raioAtual = LocalCache.getInstance().getRaio(); 
					Combustivel.ETipo tipoAtual = LocalCache.getInstance().getTipoPreferencia();
					if(ultimoRaio != raioAtual ) {
						ultimoRaio = raioAtual;
						listaPostos = PostoDAO.dao().getPostoNearby(cache.getLatitude(), cache.getLongitude(), ultimoRaio);
						alterarAdapter = true;
					} else if (ultimoTipo != tipoAtual) {
						ultimoTipo = tipoAtual;
						alterarAdapter = true;
					}
				}
				LocalCache.getInstance().setPostos(listaPostos);

				if(alterarAdapter) {
					if(listaPostos == null)
						listaPostos = LocalCache.getInstance().getPostos();
					
					runOnUiThread(new Runnable() {
						
						
						public void run() {
							inicializarListAdapter();
						}
					});
				}

			}
		}).start();
    }
    
	public void inicializarListAdapter() {

		//TextView emptyView = (TextView)findViewById(R.id.empty);
		
		//emptyView.setTextColor(android.R.color.black);
		//emptyView.setText("Nenhum posto encontrado");
		//emptyView.setTextSize(20);
		/*
		emptyView.setVisibility(View.GONE);
		emptyView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		*/
        
		setContentView(R.layout.empty_listitem);
        getListView().setEmptyView(findViewById(R.id.empty));
        
        
		if(listaPostos != null) {
			Collections.sort(listaPostos, new PostoComparator());
			
			setListAdapter(new StationsArrayAdapter(this, R.layout.listitem,
					listaPostos));
		}
		

	}

	public void myClickHandler(View v) {
		// get the row the clicked button is in
		LinearLayout vwParentRow = (LinearLayout) v;

		TextView child = (TextView) vwParentRow.getChildAt(0);
		Toast.makeText(getApplicationContext(), child.getText(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		Intent alertasActivity = new Intent(this.getApplicationContext(),
				DetalhePostoActivity.class);
		alertasActivity.putExtra("posto", listaPostos.get(position));
		this.startActivity(alertasActivity);
	}

}