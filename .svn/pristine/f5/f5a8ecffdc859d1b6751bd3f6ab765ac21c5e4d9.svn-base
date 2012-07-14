package com.sap.appsexperience.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import com.sap.appsexperience.model.Combustivel;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.Usuario;
import com.sap.appsexperience.model.Combustivel.ETipo;
import com.sap.appsexperience.model.dao.UsuarioDAO;

public class LocalCache {
	public static final String USER_FILE = "UserLocalData";

	
	private static LocalCache instance = null;
	private List<Posto> postos = new ArrayList<Posto>();
	private Usuario usuarioAtual = null;
	private Double latitude;
	private Double longitude;
	private Combustivel.ETipo tipoPreferencia = null;
	
	private Context contexto = null;
	
	private LocalCache(Context contexto) {
		this.contexto = contexto;
	}
	
	public static LocalCache initInstance(Context contexto) {
		if(instance == null)
			instance = new LocalCache(contexto);
		
		return instance;
	}
	
	public static LocalCache getInstance() {

		return instance;
	}
	
	public List<Posto> getPostos() {
		return postos;
	}
	
	public void setPostos(List<Posto> lista) {
		this.postos = lista;
	}

	public void setUsuarioAtual(Usuario usuarioAtual) {
		this.usuarioAtual = usuarioAtual;
	}
	
	public void updateUsuarioAtual() {
		if(this.usuarioAtual != null) {
			SharedPreferences settings = contexto.getSharedPreferences(USER_FILE, 0);
			
			SharedPreferences.Editor editor = settings.edit();
		    editor.putString("guid", this.usuarioAtual.getInstallationGuid());
		    editor.putString("apelido", this.usuarioAtual.getApelido());
		    editor.putLong("reputacao", this.usuarioAtual.getReputacao());
		    editor.putString("favoritos", this.usuarioAtual.getFavoritosAsString());

		    // Commit the edits!
		    editor.commit();
		}
	}

	public Usuario getUsuarioAtual() {
		if(this.usuarioAtual == null) {
			SharedPreferences settings = contexto.getSharedPreferences(USER_FILE, 0);
			String instalacao = settings.getString("guid", "");
			if(!instalacao.isEmpty()) {
				String apelido = settings.getString("apelido", "");
				Long reputacao = settings.getLong("reputacao", 0);
				String idFavoritos = settings.getString("favoritos", "");
				this.usuarioAtual = new Usuario();
				this.usuarioAtual.setApelido(apelido);
				this.usuarioAtual.setReputacao(reputacao);
				this.usuarioAtual.setInstallationGuid(instalacao);
				this.usuarioAtual.setFavoritsFromString(idFavoritos);
				
				// atualiza reputa��o
				new Thread(new Runnable() {
				    public void run() {
				      usuarioAtual.setReputacao(UsuarioDAO.dao().getReputacao(usuarioAtual.getApelido()));
				    }
				  }).start();
			}
		}
		
		return usuarioAtual;
	}

	public double getRaio() {
		return PreferenceManager.getDefaultSharedPreferences(this.contexto).getInt("raioPesquisa", 5);
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void updateTipoPreferencia() {
		this.tipoPreferencia = Combustivel.ETipo.valueOf(PreferenceManager.getDefaultSharedPreferences(this.contexto).getString("combustivelPreferencial", "gasolina"));
	}

	public Combustivel.ETipo getTipoPreferencia() {
		if(this.tipoPreferencia == null)
		 	this.tipoPreferencia = Combustivel.ETipo.valueOf(PreferenceManager.getDefaultSharedPreferences(this.contexto).getString("combustivelPreferencial", "gasolina"));
		
		return this.tipoPreferencia;
	}
	
	
	
	
}
