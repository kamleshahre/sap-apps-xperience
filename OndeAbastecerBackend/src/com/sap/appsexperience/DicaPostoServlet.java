package com.sap.appsexperience;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.Dica;
import com.sap.appsexperience.model.dao.DicaDAO;
import com.sap.appsexperience.model.dao.PostoDAO;
import com.sap.appsexperience.resources.JSONMessage;
import com.sap.appsexperience.resources.JSONParameter;

@SuppressWarnings("serial")
public class DicaPostoServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String sPosto = (String)req.getParameter("idPosto");
		Long idPosto = Long.parseLong(sPosto);
		
		Posto postoDicas = PostoDAO.dao().getOrCreatePosto(idPosto);
		
		List<Dica> dicas = DicaDAO.dao().ofy().query(Dica.class).filter("idPosto", postoDicas.getKey()).order("-data").limit(40).list();
		
		Dica_JSONMessage mensagem = new Dica_JSONMessage();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		JSONArray arrDicas = new JSONArray();
		if(dicas != null) {
			for(Dica dica : dicas) {
		
				mensagem.idPosto = sPosto;
				mensagem.idDica = String.valueOf(dica.getId());
				mensagem.criador = dica.getApelido().getName();
				mensagem.data = formatter.format(dica.getData());
				mensagem.avaliacao = String.valueOf(dica.getValorAvaliacao());
				mensagem.texto = dica.getTexto();
				
				arrDicas.put(mensagem.getJSON());
				
			}
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().println(arrDicas.toString());
		
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
