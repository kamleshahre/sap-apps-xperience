package com.sap.appsexperience;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONObject;
import com.sap.appsexperience.model.Usuario;
import com.sap.appsexperience.model.dao.UsuarioDAO;
import com.sap.appsexperience.resources.JSONMessage;
import com.sap.appsexperience.resources.JSONParameter;

@SuppressWarnings("serial")
public class PerfilUsuarioServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String apelido = (String)req.getParameter("apelido");
		if(apelido == null)
			return;
		
		Usuario_JSONMessage mensagem = new Usuario_JSONMessage();
		
		Usuario perfil = UsuarioDAO.dao().ofy().find(Usuario.class, apelido);
		if(perfil != null) {
			mensagem.apelido = perfil.getApelido();
			mensagem.reputacao = String.valueOf(perfil.getReputacao());
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().println(mensagem.getJSON().toString());
		
	}
	
	private class Usuario_JSONMessage extends JSONMessage {
		
		@JSONParameter public String apelido = "";
		@JSONParameter public String reputacao = "";
		
	}

}
