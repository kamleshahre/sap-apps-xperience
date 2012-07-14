package com.sap.appsexperience.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sap.appsexperience.util.LocalCache;


public class Usuario implements Serializable {
    private String apelido;
	
	private String email;
	
	private String installationGuid;
	
	private long reputacao;
	
	private ArrayList<Long> idPostoFavorito = new ArrayList<Long>();

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getApelido() {
		return apelido;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setInstallationGuid(String installationGuid) {
		this.installationGuid = installationGuid;
	}

	public String getInstallationGuid() {
		return installationGuid;
	}

	public void setReputacao(long reputacao) {
		this.reputacao = reputacao;
	}

	public long getReputacao() {
		return reputacao;
	}

	public void setFavoritsFromString(String idFavoritos) {
		String[] arrFavoritos = idFavoritos.split(";");
		for(String sFav : arrFavoritos) {
			if(!sFav.isEmpty()) {
				this.idPostoFavorito.add(Long.valueOf(sFav));
			}
		}
		
	}

	public String getFavoritosAsString() {
		StringBuilder sbFavoritos = new StringBuilder();
		for(Long lFav : this.idPostoFavorito) {
			sbFavoritos.append(lFav.toString());
			sbFavoritos.append(";");
		}
		return sbFavoritos.toString();
	}

	public void removerFavorito(Posto postoFavorito) {
		if(!this.idPostoFavorito.contains(postoFavorito.getId())) {
			this.idPostoFavorito.add(postoFavorito.getId());
			new Thread(new Runnable() {
			    public void run() {
			      LocalCache.getInstance().updateUsuarioAtual();
			    }
			}).start();
		}
	}

	public void adicionarFavorito(Posto postoFavorito) {
		this.idPostoFavorito.remove(postoFavorito.getId());
		new Thread(new Runnable() {
		    public void run() {
		      LocalCache.getInstance().updateUsuarioAtual();
		    }
		}).start();
	}

	public boolean verificaFavorito(Posto postoFavorito) {
		return this.idPostoFavorito.contains(postoFavorito.getId());
	}
	
}
