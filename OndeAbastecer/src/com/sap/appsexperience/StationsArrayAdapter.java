package com.sap.appsexperience;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sap.appsexperience.model.Combustivel;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.Combustivel.ETipo;
import com.sap.appsexperience.util.LocalCache;

public class StationsArrayAdapter extends ArrayAdapter<Posto> {

	private static final String tag = "PostoArrayAdapter";
	private Context context;

	private ImageView imgStationFlag;
	private TextView txtStationName;
	private TextView txtPrice;
	private TextView txtDistance;
	private List<Posto> postosList = new ArrayList<Posto>();
	private int[] colors = new int[] { Color.WHITE,
			Color.parseColor("#FFF3F3F3") };

	public StationsArrayAdapter(Context context, int textViewResourceId,
			List<Posto> postos) {
		super(context, textViewResourceId, postos);
		this.context = context;
		this.postosList = postos;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listitem, null);
		}

		int colorPos = position % colors.length;
		row.setBackgroundColor(colors[colorPos]);

		Posto item = postosList.get(position);
		imgStationFlag = (ImageView) row.findViewById(R.id.stationFlag);
		txtStationName = (TextView) row.findViewById(R.id.stationName);
		txtDistance = (TextView) row.findViewById(R.id.distance);
		txtPrice = (TextView) row.findViewById(R.id.price);

		txtStationName.setText(item.getNome());

		double distance = (item.ultimaDistancia + (item.ultimaDistancia * .3)) / 1000;

		if (distance < 1.0)
			txtDistance.setText("- 1Km");
		else {
			String outStr = String.format("%.1f km", distance);
			txtDistance.setText(outStr);
		}

		StringBuilder sb = new StringBuilder();
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		nf.setCurrency(Currency.getInstance("BRL"));
		nf.setMaximumFractionDigits(3);
		
		for (Combustivel combustivel : item.getCombustivelNoPosto()) {

			if(combustivel.getPrecoAtual() != null) {
				if(combustivel.getTipo() == LocalCache.getInstance().getTipoPreferencia()) {
					if (combustivel.getTipo() == ETipo.gasolina) {
						sb.append("Gasolina ");
						sb.append(nf.format(combustivel.getPrecoAtual().getPreco()));
					} else if (combustivel.getTipo() == ETipo.alcool) {
						sb.append("Etanol ");
						sb.append(nf.format(combustivel.getPrecoAtual().getPreco()));
					} else if(combustivel.getTipo() == ETipo.diesel) {
						sb.append("Diesel ");
						sb.append(nf.format(combustivel.getPrecoAtual().getPreco()));
					}
				}
			}
		}
		

			//"G: " + precoGasolina + "   A: " + precoAlcohol
		txtPrice.setText(sb.toString());

		String imgFilePath = "imagens/ic_"
				+ item.getBandeira().toString().toLowerCase() + ".png";

		try {
			Bitmap bitmap = BitmapFactory.decodeStream(this.context
					.getResources().getAssets().open(imgFilePath));
			imgStationFlag.setImageBitmap(bitmap);

		} catch (IOException e) {
			try {
				imgStationFlag.setImageBitmap(BitmapFactory
						.decodeStream(this.context.getResources().getAssets()
								.open("imagens/ic_branco.png")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return row;
	}

}
