package com.sap.appsexperience.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Id;
import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.sap.appsexperience.model.dao.CombustivelDAO;

public class Posto {

	@Id Long id;
	private Long idOrigem; // utiliza o id da ANP
	EBandeira bandeira;
	double latitude;
	double longitude;

	String nome = "";

	String endereco = "";
	String cidade = "";
	String uf = "";

	Collection<Key<Combustivel>> idCombustiveis;
	@Transient
	Collection<Combustivel> combustivelNoPosto;

	public enum EBandeira {
		nenhuma("branca.png"), BR("br.png"), shell("shell.png"), esso(
				"esso.png"), ipiranga("ipiranga.png"), texaco("texaco.png");

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

	private Collection<Combustivel> getCombustivelNoPosto() {
		if(combustivelNoPosto == null) { // lazy loading de combustiveis
			if(this.idCombustiveis != null)
				this.combustivelNoPosto = CombustivelDAO.dao().getList(this.idCombustiveis);
			if(this.combustivelNoPosto == null)
				this.combustivelNoPosto = new ArrayList<Combustivel>();
		}

		return combustivelNoPosto;
	}
	
	public void addCombustivel(Combustivel combustivel) {
		this.getCombustivelNoPosto().add(combustivel);
		this.getIdCombustiveis().add(combustivel.getKey());
	}
	
	private Collection<Key<Combustivel>> getIdCombustiveis() {
		if(this.idCombustiveis == null)
			this.idCombustiveis = new ArrayList<Key<Combustivel>>();
		
		return this.idCombustiveis;
	}

	public Combustivel getCombustivel(Combustivel.ETipo tipo) {
		for(Combustivel combustivel : this.getCombustivelNoPosto()) {
			if(combustivel.tipo.equals(tipo))
				return combustivel;
		}
		// Não encontrou? adiciona..
		Combustivel combustivel = new Combustivel(tipo, this);
		CombustivelDAO.dao().save(combustivel);
		this.addCombustivel(combustivel);
		
		return combustivel;
	}

	public Key<Posto> getKey() {
		return new Key<Posto>(Posto.class, this.getId());
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
}
