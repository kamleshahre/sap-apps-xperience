package com.sap.appsexperience.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class PrecoHistorico implements Serializable {

	private Long id;
	private Combustivel combustivelPai;
	
	private Date dataAtualizacao;
	private double preco;
	private String responsavel;
	
	public PrecoHistorico(Long id, Combustivel combustivel, String responsavel) {
		this.id = id;
		this.dataAtualizacao = Calendar.getInstance().getTime();
		this.responsavel = responsavel;
		this.setCombustivelPai(combustivel);
	}
	
	public PrecoHistorico(Combustivel combustivel, Date dataAtualizacao, double preco, String responsavel ) {
		this.setCombustivelPai(combustivel);
		this.dataAtualizacao = dataAtualizacao;
		this.preco = preco;
		this.responsavel = responsavel;
	}
	
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public String getResponsavel() {
		return this.responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Long getId() {
		return id;
	}

	public void setCombustivelPai(Combustivel combustivelPai) {
		this.combustivelPai = combustivelPai;
	}

	public Combustivel getCombustivelPai() {
		return combustivelPai;
	}
}
