package com.sap.appsexperience.model;

import java.util.Calendar;
import java.util.Date;

public class Abastecimento {
	public Long id;
	public Usuario usuario;
	public PrecoHistorico preco;
	
	public Date data;
	public double litros;
	public double valorTotal;
	
	
	public Abastecimento(PrecoHistorico preco, Usuario usuario, double litros, double valorTotal) {
		this.setData(Calendar.getInstance().getTime());
		this.preco = preco;
		this.usuario = usuario;
		this.litros = litros;
		this.valorTotal = valorTotal;
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
