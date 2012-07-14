package com.sap.appsexperience.model;

import java.util.UUID;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

public class Usuario {

	@Id private String apelido;
	
	private String email;
	private String nome4square;
	
	private String hashPwd;
	private String installationGuid;
	
	private long reputacaoCalculada;
	
	private Usuario() {}
	
	public Usuario(String apelido) {
		this.setApelido(apelido);
		setInstallationGuid(UUID.randomUUID().toString());
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome4square() {
		return nome4square;
	}

	public void setNome4square(String nome4square) {
		this.nome4square = nome4square;
	}

	public String getInstallationGuid() {
		return installationGuid;
	}

	public void setInstallationGuid(String installationGuid) {
		this.installationGuid = installationGuid;
	}

	public String getHashPwd() {
		return hashPwd;
	}

	public void setHashPwd(String hashPwd) {
		this.hashPwd = hashPwd;
	}
	
	public void addItemReputacao(ItemReputacao novaReputacao) {
		this.setReputacaoCalculada(this.getReputacao() + novaReputacao.motivo.pesoReputacao());
		novaReputacao.usuarioDestino = this.getKey();
	}
	
	public void removeItemReputacao(ItemReputacao removerReputacao) {
		this.setReputacaoCalculada(this.getReputacao() - removerReputacao.motivo.pesoReputacao());
	}
	
	public Key<Usuario> getKey() {
		return new Key<Usuario>(Usuario.class, this.getApelido());
	}

	public long getReputacao() {
		return reputacaoCalculada;
	}

	private void setReputacaoCalculada(long reputacaoCalculada) {
		this.reputacaoCalculada = reputacaoCalculada;
	}
	
}
