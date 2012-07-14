package com.sap.appsexperience.model;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.sap.appsexperience.model.dao.CombustivelDAO;
import com.sap.appsexperience.model.dao.PrecoHistoricoDAO;

public class Combustivel {
	
	@Id Long id;
	@Parent Key<Posto> posto;
	
	ETipo tipo;
	
	Key<PrecoHistorico> idPrecoAtual;
	
	@Transient PrecoHistorico precoAtual = null;
	@Transient List<PrecoHistorico> historico = null;
	
	public enum ETipo {
		gasolina, alcool, diesel;
	}
	
	private Combustivel() {}
	
	public Combustivel(ETipo tipo, Posto posto) {
		this.tipo = tipo;
		this.posto = posto.getKey();
	}
	
	public void atualizarPreco(PrecoHistorico preco) {
		if(getPrecoAtual() != null) {
			if(!getPrecoAtual().antesDe(preco)) {
				return;
			}
		}
		
		if(preco != null) {
			PrecoHistoricoDAO.dao().save(preco); //pode persistir
			this.setPrecoAtual(preco);
		}
	}

	public PrecoHistorico getPrecoAtual() {
		if(precoAtual == null)
			if(idPrecoAtual != null)
				precoAtual = PrecoHistoricoDAO.dao().get(idPrecoAtual);
		
		return precoAtual;
	}

	private void setPrecoAtual(PrecoHistorico precoAtual) {
		Key<PrecoHistorico> chave = new Key<PrecoHistorico>(this.getKey(), PrecoHistorico.class, precoAtual.getId());
		precoAtual.setCombustivelNoPosto(this.getKey());
		this.idPrecoAtual = chave;
		this.precoAtual = precoAtual;
	}

	public Key<Combustivel> getKey() {
		return new Key<Combustivel>(this.posto, Combustivel.class, this.getId());
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

	/*public List<PrecoHistorico> getHistorico() {
		return historico;
	}

	public void setHistorico(List<PrecoHistorico> historico) {
		this.historico = historico;
	}*/
}
