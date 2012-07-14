package com.sap.appsexperience.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.sap.appsexperience.model.dao.UsuarioDAO;

public class PrecoHistorico {
	
	@Id private Long id;
	@Parent private Key<Combustivel> combustivelNoPosto;
	
	private Date dataAtualizacao;
	private double preco;
	private Key<Usuario> responsavel;
	
	private PrecoHistorico() {}
	
	public PrecoHistorico(Combustivel combustivel, Usuario responsavel) {
		this.dataAtualizacao = Calendar.getInstance().getTime();
		this.responsavel = responsavel.getKey();
		this.combustivelNoPosto = combustivel.getKey();
	}
	
	public PrecoHistorico(Combustivel combustivel, Date dataAtualizacao, double preco, Usuario responsavel ) {
		this.combustivelNoPosto = combustivel.getKey();
		this.dataAtualizacao = dataAtualizacao;
		this.preco = preco;
		this.responsavel = responsavel.getKey();
	}
	
	public Key<PrecoHistorico> getKey() {
		return new Key<PrecoHistorico>(this.combustivelNoPosto, PrecoHistorico.class, this.getId());
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
	public Usuario getResponsavel() {
		return UsuarioDAO.dao().ofy().get(responsavel);
	}
	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel.getKey();
	}
	public boolean antesDe(PrecoHistorico precoComparar) {
		if(precoComparar == null)
			return false;
		
		if(this.dataAtualizacao.compareTo(precoComparar.getDataAtualizacao()) < 0)
			return true;
		else
			return false;
	}

	public Long getId() {
		return id;
	}

	public Key<Combustivel> getCombustivelNoPosto() {
		return combustivelNoPosto;
	}

	public void setCombustivelNoPosto(Key<Combustivel> combustivelNoPosto) {
		this.combustivelNoPosto = combustivelNoPosto;
	}
}
