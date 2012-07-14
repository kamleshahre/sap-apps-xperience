package com.sap.appsexperience.model;

import java.io.Serializable;

public class Combustivel implements Serializable {

	Long id;
	Posto posto;
	
	ETipo tipo;
	
	PrecoHistorico precoAtual = null;
	
	public enum ETipo {
		gasolina, alcool, diesel;
	}
	
	public Combustivel(Long id, ETipo tipo, Posto posto) {
		this.id = id;
		this.tipo = tipo;
		this.setPosto(posto);
	}

	public Long getId() {
		return id;
	}

	public ETipo getTipo() {
		return tipo;
	}

	public void setTipo(ETipo tipo) {
		this.tipo = tipo;
	}

	public void setPrecoAtual(PrecoHistorico precoAtual) {
		this.precoAtual = precoAtual;
	}

	public PrecoHistorico getPrecoAtual() {
		return precoAtual;
	}

	public void setPosto(Posto posto) {
		this.posto = posto;
	}

	public Posto getPosto() {
		return posto;
	}
}
