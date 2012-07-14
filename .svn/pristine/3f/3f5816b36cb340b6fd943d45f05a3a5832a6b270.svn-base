package com.sap.appsexperience.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;

public class Dica {

	@Id private Long id;
	@Parent Key<Posto> idPosto;
	
	private Key<Usuario> apelido;
	private String texto;
	private Date data;
	
	private long valorAvaliacao;
	
	private Dica() {}
	
	public Dica(Posto posto, Usuario criador, String dica) {
		this.idPosto = posto.getKey();
		this.setApelido(criador.getKey());
		this.setTexto(dica);
		this.setData(Calendar.getInstance().getTime());
	}

	void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}

	public void addValorAvaliacao(short valorAvaliacao) {
		this.valorAvaliacao += valorAvaliacao;
	}

	public long getValorAvaliacao() {
		return valorAvaliacao;
	}

	public void setApelido(Key<Usuario> apelido) {
		this.apelido = apelido;
	}

	public Key<Usuario> getApelido() {
		return apelido;
	}

	private void setData(Date data) {
		this.data = data;
	}

	public Date getData() {
		return data;
	}

	public Key<Dica> getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
