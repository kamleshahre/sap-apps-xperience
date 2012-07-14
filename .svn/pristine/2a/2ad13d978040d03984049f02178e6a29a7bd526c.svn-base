package com.sap.appsexperience.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

public class Abastecimento {
	@Id Long id;
	Key<Usuario> usuario;
	Key<PrecoHistorico> preco;
	
	private Date data;
	private double litros;
	private double valorTotal;
	
	private Abastecimento() {}
	
	public Abastecimento(PrecoHistorico preco, Usuario usuario) {
		this.setData(Calendar.getInstance().getTime());
		this.preco = preco.getKey();
		this.usuario = usuario.getKey();
	}

	public long getId() {
		return id;
	}

	public double getLitros() {
		return litros;
	}

	public void setLitros(double litros) {
		this.litros = litros;
	}

	public Date getData() {
		return data;
	}

	void setData(Date data) {
		this.data = data;
	}

	double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valor) {
		this.valorTotal = valor;
	}
}
