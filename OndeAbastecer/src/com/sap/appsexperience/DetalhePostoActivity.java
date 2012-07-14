package com.sap.appsexperience;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;

import com.sap.appsexperience.model.Combustivel;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.util.LocalCache;
import com.sap.appsexperience.util.MenuHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DetalhePostoActivity extends Activity {

	private Posto postoAtual;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhe_posto);

		this.postoAtual = (Posto) this.getIntent().getExtras().get("posto");
		if (this.postoAtual != null) {
			TextView lblNomePosto = (TextView) findViewById(R.id.nomePosto);
			lblNomePosto.setText(this.postoAtual.getNome());

			TextView lblEndereco = (TextView) findViewById(R.id.enderecoPosto);
			lblEndereco.setText(this.postoAtual.getEndereco());

			String uri = "drawable/"
					+ this.postoAtual.getBandeira().getFigura()
							.replace(".png", "");
			int imageResource = getResources().getIdentifier(uri, null,
					getPackageName());
			ImageView imgView = (ImageView) findViewById(R.id.stationFlag);
			Drawable image = getResources().getDrawable(imageResource);
			imgView.setImageDrawable(image);

			LinearLayout layoutGasolina = (LinearLayout) findViewById(R.id.layoutPrecoGasolina);
			TextView lblValorGasolina = (TextView) findViewById(R.id.valorGasolina);
			TextView lblDataGasolina = (TextView) findViewById(R.id.dataGasolina);
			TextView lblQuemGasolina = (TextView) findViewById(R.id.quemGasolina);
			
			LinearLayout layoutAlcool = (LinearLayout) findViewById(R.id.layoutPrecoAlcool);
			TextView lblValorAlcool = (TextView) findViewById(R.id.valorAlcool);
			TextView lblDataAlcool = (TextView) findViewById(R.id.dataAlcool);
			TextView lblQuemAlcool = (TextView) findViewById(R.id.quemAlcool);
			
			LinearLayout layoutDiesel = (LinearLayout) findViewById(R.id.layoutPrecoDiesel);
			TextView lblValorDiesel = (TextView) findViewById(R.id.valorDiesel);
			TextView lblDataDiesel = (TextView) findViewById(R.id.dataDiesel);
			TextView lblQuemDiesel = (TextView) findViewById(R.id.quemDiesel);

			layoutGasolina.setVisibility(View.GONE);
			layoutAlcool.setVisibility(View.GONE);
			layoutDiesel.setVisibility(View.GONE);

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			nf.setCurrency(Currency.getInstance("BRL"));
			nf.setMaximumFractionDigits(3);

			for (Combustivel combustivel : this.postoAtual.getCombustivelNoPosto()) {

				if (combustivel.getPrecoAtual() != null) {
					if (combustivel.getPrecoAtual().getPreco() > 0) {
						switch (combustivel.getTipo()) {
							case gasolina:
								layoutGasolina.setVisibility(View.VISIBLE);
								lblValorGasolina.setText(nf.format(combustivel.getPrecoAtual().getPreco()));
								lblDataGasolina.setText(df.format(combustivel.getPrecoAtual().getDataAtualizacao()));
								lblQuemGasolina.setText("por " + combustivel.getPrecoAtual().getResponsavel());
								break;
							case alcool:
								layoutAlcool.setVisibility(View.VISIBLE);
								lblValorAlcool.setText(nf.format(combustivel.getPrecoAtual().getPreco()));
								lblDataAlcool.setText(df.format(combustivel.getPrecoAtual().getDataAtualizacao()));
								lblQuemAlcool.setText("por " + combustivel.getPrecoAtual().getResponsavel());
								break;
							case diesel:
								layoutDiesel.setVisibility(View.VISIBLE);
								lblValorDiesel.setText(nf.format(combustivel.getPrecoAtual().getPreco()));
								lblDataDiesel.setText(df.format(combustivel.getPrecoAtual().getDataAtualizacao()));
								lblQuemDiesel.setText("por " + combustivel.getPrecoAtual().getResponsavel());
								break;
						}

					}
				}

			}
			
			ToggleButton tglAdicionarFavoritos = (ToggleButton)findViewById(R.id.AdicionarFavorito);
			
			if(LocalCache.getInstance().getUsuarioAtual().verificaFavorito(postoAtual)) {
				tglAdicionarFavoritos.setChecked(true);
			} else {
				tglAdicionarFavoritos.setChecked(false);
			}
			
			tglAdicionarFavoritos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				public void onCheckedChanged(CompoundButton btn, boolean val) {
					if(val) {
						LocalCache.getInstance().getUsuarioAtual().removerFavorito(postoAtual);
					} else {
						LocalCache.getInstance().getUsuarioAtual().adicionarFavorito(postoAtual);
					}
					
				}
			});

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return MenuHandler.onCreateOptionsMenu(getMenuInflater(), menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (MenuHandler.onOptionsItemSelected(item, this)) {
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	public void abastecerAqui(View view) {
		Intent abastecerAqui = new Intent(this.getApplicationContext(),
				AbastecerActivity.class);
		abastecerAqui.putExtra("posto", postoAtual);
		this.startActivity(abastecerAqui);
	}
}
