package com.sap.appsexperience;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.sap.appsexperience.model.ItemReputacao;
import com.sap.appsexperience.model.ItemReputacao.EMotivoReputacao;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.Dica;
import com.sap.appsexperience.model.Usuario;
import com.sap.appsexperience.model.dao.DicaDAO;
import com.sap.appsexperience.model.dao.PostoDAO;
import com.sap.appsexperience.model.dao.UsuarioDAO;
import com.sap.appsexperience.resources.JSONMessage;
import com.sap.appsexperience.resources.JSONParameter;

@SuppressWarnings("serial")
public class DicaPostoCadastrarServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String sPosto = (String)req.getParameter("idPosto");
		String sApelido =  (String)req.getParameter("apelido");
		String sTexto =  (String)req.getParameter("texto");
		
		Long idPosto = Long.parseLong(sPosto);
		
		Posto postoDicas = PostoDAO.dao().getOrCreatePosto(idPosto);
		Usuario criador = UsuarioDAO.dao().getOrCreateUsuario(sApelido);
		
		Dica_JSONMessage mensagem = new Dica_JSONMessage();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		Dica novaDica = new Dica(postoDicas, criador, sTexto);
		DicaDAO.dao().save(novaDica);
		ItemReputacao.pontuar(EMotivoReputacao.NovaDica, criador, criador, novaDica);
		
		mensagem.idPosto = sPosto;
		mensagem.idDica = String.valueOf(novaDica.getId());
		mensagem.criador = novaDica.getApelido().getName();
		mensagem.data = formatter.format(novaDica.getData());
		mensagem.avaliacao = String.valueOf(novaDica.getValorAvaliacao());
		mensagem.texto = novaDica.getTexto();
		
		resp.setContentType("text/plain");
		resp.getWriter().println(mensagem.getJSON().toString());
		
	}
	
	private class Dica_JSONMessage extends JSONMessage {
		
		@JSONParameter public String idPosto = "";
		@JSONParameter public String idDica = "";
		@JSONParameter public String criador = "";
		@JSONParameter public String data = "";
		@JSONParameter public String avaliacao = "";
		@JSONParameter public String texto = "";
		
	}

}
