package com.sap.appsexperience;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.sap.appsexperience.model.Abastecimento;
import com.sap.appsexperience.model.Combustivel;
import com.sap.appsexperience.model.Combustivel.ETipo;
import com.sap.appsexperience.model.ItemReputacao.EMotivoReputacao;
import com.sap.appsexperience.model.ItemReputacao;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.PrecoHistorico;
import com.sap.appsexperience.model.Usuario;
import com.sap.appsexperience.model.dao.AbastecimentoDAO;
import com.sap.appsexperience.model.dao.CombustivelDAO;
import com.sap.appsexperience.model.dao.ItemReputacaoDAO;
import com.sap.appsexperience.model.dao.PostoDAO;
import com.sap.appsexperience.model.dao.UsuarioDAO;
import com.sap.appsexperience.resources.JSONMessage;
import com.sap.appsexperience.resources.JSONParameter;

@SuppressWarnings("serial")
public class AbastecerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sApelido = (String)req.getParameter("apelido");
		String sInstalacao = (String)req.getParameter("instalacao");
		String sPosto = (String)req.getParameter("posto");
		String sTipo = (String)req.getParameter("tipo");
		String sValorLitro = (String)req.getParameter("valorLitro");
		String sLitros = (String)req.getParameter("litros");
		String sTotal = (String)req.getParameter("total");
		boolean pontuarCadastro = false;
		
		Long idPosto = Long.parseLong(sPosto);
		Double dblValorLitro = Double.valueOf(sValorLitro);
		Double dblLitros = Double.valueOf(sLitros);
		Double dblValorTotal = Double.valueOf(sTotal);
		
		// recupera usuario
		Usuario usuarioAbastecendo = UsuarioDAO.dao().getOrCreateUsuario(sApelido);
		Posto postoAbastecendo = PostoDAO.dao().getOrCreatePosto(idPosto);
		ETipo tipoCombustivel = ETipo.valueOf(sTipo);
		Combustivel combustivel = postoAbastecendo.getCombustivel(tipoCombustivel);
		
		PrecoHistorico precoAbastecimento;
		
		long pontuacaoAntes = usuarioAbastecendo.getReputacao();
		
		if(combustivel.getPrecoAtual() != null) {
			// confirmar ou alterar?
			Usuario pontuar = combustivel.getPrecoAtual().getResponsavel();
			
			if(dblValorLitro.equals(combustivel.getPrecoAtual().getPreco())) {
				precoAbastecimento = combustivel.getPrecoAtual();
				
				if(!usuarioAbastecendo.getApelido().equals(pontuar.getApelido())) {
					// pontua quem cadastrou e quem confirmou
					ItemReputacao pontoResponsavel = new ItemReputacao(EMotivoReputacao.ValorCombustivelConfirmado, usuarioAbastecendo);
					pontuar.addItemReputacao(pontoResponsavel);
					
					ItemReputacao pontoConfirmando = new ItemReputacao(EMotivoReputacao.ConfirmarValor, usuarioAbastecendo);
					usuarioAbastecendo.addItemReputacao(pontoConfirmando);
					
					ItemReputacaoDAO.dao().save(pontoResponsavel, pontoConfirmando);
					UsuarioDAO.dao().save(pontuar, usuarioAbastecendo);
				}
				
			} else {
				// verifica diferença de datas
				long diff = combustivel.getPrecoAtual().getDataAtualizacao().getTime() - Calendar.getInstance().getTimeInMillis();
				diff = ( ( ( diff / 1000 ) / 60 ) / 60 ); // converte de ms para hrs
				
				// se a diferença for maior que 20hrs, remove pontos
				if(usuarioAbastecendo.getReputacao() > 5) {
					if(diff <= 20 && diff >= 0) {
						//remove pontos de quem cadastrou
						ItemReputacao pontoResponsavel = new ItemReputacao(EMotivoReputacao.ValorCombustivelErrado, usuarioAbastecendo);
						pontuar.addItemReputacao(pontoResponsavel);
						ItemReputacaoDAO.dao().save(pontoResponsavel);
						UsuarioDAO.dao().save(pontuar);
					}
				}
				
				// novo cadastro
				PrecoHistorico novoPreco = new PrecoHistorico(combustivel, usuarioAbastecendo);
				novoPreco.setPreco(dblValorLitro);
				combustivel.atualizarPreco(novoPreco);
				CombustivelDAO.dao().save(combustivel);
				pontuarCadastro = true;
				
				precoAbastecimento = novoPreco;
				
			}
			
		} else {
			// novo preço
			PrecoHistorico novoPreco = new PrecoHistorico(combustivel, usuarioAbastecendo);
			novoPreco.setPreco(dblValorLitro);
			combustivel.atualizarPreco(novoPreco);
			CombustivelDAO.dao().save(combustivel);
			pontuarCadastro = true;
			
			precoAbastecimento = novoPreco;
			
		}
		
		// pontua em caso de cadastro de algum valor
		if(pontuarCadastro) {
			ItemReputacao pontoCadastro = new ItemReputacao(EMotivoReputacao.CadastraValor, usuarioAbastecendo);
			usuarioAbastecendo.addItemReputacao(pontoCadastro);
			ItemReputacaoDAO.dao().save(pontoCadastro);
			UsuarioDAO.dao().save(usuarioAbastecendo);
		}
		
		// adiciona abastecimento
		Abastecimento novoAbastecimento = new Abastecimento(precoAbastecimento, usuarioAbastecendo);
		novoAbastecimento.setLitros(dblLitros);
		novoAbastecimento.setValorTotal(dblValorTotal);
		AbastecimentoDAO.dao().save(novoAbastecimento);
		
		// verifica pontuacao nova
		long pontuacaoDepois = usuarioAbastecendo.getReputacao();
		
		Abastecimento_JSONMessage mensagem = new Abastecimento_JSONMessage();
		mensagem.pontosRecebidos = String.valueOf((pontuacaoAntes - pontuacaoDepois));
		
		resp.setContentType("text/plain");
		resp.getWriter().println(mensagem.getJSON().toString());
		
	}
	
	private class Abastecimento_JSONMessage extends JSONMessage {
		
		@JSONParameter public String pontosRecebidos = "";
		
	}
}
