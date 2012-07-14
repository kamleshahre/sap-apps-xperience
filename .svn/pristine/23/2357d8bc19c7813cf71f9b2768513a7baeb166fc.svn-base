package com.sap.appsexperience.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.sap.appsexperience.model.dao.ItemReputacaoDAO;
import com.sap.appsexperience.model.dao.UsuarioDAO;

public class ItemReputacao {

	@Id Long id;
	
	Key<Usuario> usuarioDestino;
	Key<Usuario> responsavel;
	private Key<Dica> dicaRelacionada;
	
	EMotivoReputacao motivo;
	Date data;
	
	private ItemReputacao() {}
	
	public ItemReputacao(EMotivoReputacao motivo, Usuario responsavel) {
		this.motivo = motivo;
		this.responsavel = responsavel.getKey();
		this.data = Calendar.getInstance().getTime();
	}
	
	public ItemReputacao(EMotivoReputacao motivo, Usuario responsavel, Usuario destino) {
		this.motivo = motivo;
		this.responsavel = responsavel.getKey();
		this.usuarioDestino = destino.getKey();
		this.data = Calendar.getInstance().getTime();
	}
	
	public void setDicaRelacionada(Key<Dica> dicaRelacionada) {
		this.dicaRelacionada = dicaRelacionada;
	}

	public Key<Dica> getDicaRelacionada() {
		return dicaRelacionada;
	}
	
	public static void pontuar(EMotivoReputacao motivo, Usuario usuarioOrigem, Usuario usuarioDestino, Dica dica) {
		ItemReputacao pontoCadastro = new ItemReputacao(motivo, usuarioOrigem);
		pontoCadastro.setDicaRelacionada(dica.getKey());
		usuarioDestino.addItemReputacao(pontoCadastro);
		ItemReputacaoDAO.dao().save(pontoCadastro);
		UsuarioDAO.dao().save(usuarioDestino);
	}

	public enum EMotivoReputacao {
		NovaDica((short)2), 
		DicaBoa((short)1),
		DicaRUim((short)-1),
		ValorCombustivelConfirmado((short)6), 
		ValorCombustivelErrado((short)-5), 
		ConfirmarValor((short)1), 
		CadastraValor((short)1);
		
		EMotivoReputacao(short peso) {
			this.pesoReputacao = peso;
		}
		private short pesoReputacao;
		public short pesoReputacao() {
			return pesoReputacao;
		}
	}
	
	
	
}
