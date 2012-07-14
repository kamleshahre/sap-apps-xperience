package com.sap.appsexperience;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.sap.appsexperience.model.Usuario;
import com.sap.appsexperience.model.dao.UsuarioDAO;

@SuppressWarnings("serial")
public class NovaInstalacaoServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String apelido = (String)req.getParameter("apelido");
		if(apelido == null)
			return;
		
		JSONObject json = new JSONObject();
		
		Usuario novouser = UsuarioDAO.dao().newInstallation(apelido);
		try {
			if(novouser == null) {
				json.put("erro", true);
			} else {
				json.put("erro", false);
				json.put("installation", novouser.getInstallationGuid());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().println(json.toString());
	}

}
