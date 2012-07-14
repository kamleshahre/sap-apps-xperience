package com.sap.appsexperience.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.sap.appsexperience.model.Combustivel.ETipo;

import android.os.Parcel;
import android.os.Parcelable;

public class Posto implements Serializable {
	Long id;
	private Long idOrigem; // utiliza o id da ANP
	EBandeira bandeira;
	double latitude;
	double longitude;

	String nome = "";

	String endereco = "";
	String cidade = "";
	String uf = "";

	List<Combustivel> combustivelNoPosto;
	
	public double ultimaDistancia = 0;

	public enum EBandeira {
		nenhuma("ic_branco.png"), BR("ic_br.png"), shell("ic_shell.png"), esso(
				"ic_esso.png"), ipiranga("ic_ipiranga.png"), texaco("ic_texaco.png"), user("ic_user.png");

		EBandeira(String figura) {
			nomeFigura = figura;
		}

		String nomeFigura;

		public static EBandeira fromFigura(String text) {
			if (text != null) {
				for (EBandeira b : EBandeira.values()) {
					if (text.equalsIgnoreCase(b.nomeFigura)) {
						return b;
					}
				}
			}
			return EBandeira.nenhuma;
		}
		
		public String getFigura() {
			return nomeFigura;
		}

	}
	
	public Posto() {}
	
	public Posto(long id) {
		this.setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EBandeira getBandeira() {
		return bandeira;
	}

	public void setBandeira(EBandeira bandeira) {
		this.bandeira = bandeira;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Long getIdOrigem() {
		return idOrigem;
	}

	public void setIdOrigem(Long idOrigem) {
		this.idOrigem = idOrigem;
	}

	public void addCombustivelNoPosto(Combustivel combustivel) {
		if(this.combustivelNoPosto == null)
			this.combustivelNoPosto = new ArrayList<Combustivel>();
		
		this.combustivelNoPosto.add(combustivel);
	}

	public List<Combustivel> getCombustivelNoPosto() {
		return combustivelNoPosto;
	}
	
	public Combustivel getCombustivelByTipo(ETipo tipo) {
		for(Combustivel combustivel : this.getCombustivelNoPosto()) {
			if(combustivel.getTipo().equals(tipo)) {
				return combustivel;
			}
		}
		
		return null;
	}
}
