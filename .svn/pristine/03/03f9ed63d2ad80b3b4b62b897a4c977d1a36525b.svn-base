package com.sap.appsexperience;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.Usuario;
import com.sap.appsexperience.model.Posto.EBandeira;
import com.sap.appsexperience.model.dao.PostoDAO;
import com.sap.appsexperience.model.dao.UsuarioDAO;
import com.sap.appsexperience.resources.JSONMessage;
import com.sap.appsexperience.resources.JSONParameter;

@SuppressWarnings("serial")
public class CadastrarPostoServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String sBandeira = (String)req.getParameter("bandeira");
		String sLatitude = (String)req.getParameter("latitude");
		String sLongitude = (String)req.getParameter("longitude");
		String sNome = (String)req.getParameter("nome");
		String sEndereco = (String)req.getParameter("endereco");
		String sCidade = (String)req.getParameter("cidade");
		String sUF = (String)req.getParameter("uf");
		
		Double dblLat = Double.valueOf(sLatitude);
		Double dblLng = Double.valueOf(sLongitude);
		
		Posto novoPosto = new Posto();
		
		novoPosto.setBandeira(EBandeira.valueOf(sBandeira));
		novoPosto.setLatitude(dblLat);
		novoPosto.setLongitude(dblLng);
		novoPosto.setNome(sNome);
		novoPosto.setEndereco(sEndereco);
		novoPosto.setCidade(sCidade);
		novoPosto.setUf(sUF);
		
		PostoDAO.dao().save(novoPosto);
		
		JSONMessage mensagem = postoToJson(novoPosto);
		
		resp.setContentType("text/plain");
		resp.getWriter().println(mensagem.getJSON().toString());
	}
	
	private Posto_JSONMessage postoToJson(Posto posto) { // encapsular em uma classe e reutilizar aqui e no procurar..
		Posto_JSONMessage mensagem = new Posto_JSONMessage();
		mensagem.id = posto.getId().toString();
		mensagem.bandeira = posto.getBandeira().toString();
		mensagem.latitude = String.valueOf(posto.getLatitude());
		mensagem.longitude = String.valueOf(posto.getLongitude());
		mensagem.nome = posto.getNome();
		mensagem.endereco = posto.getEndereco();
		mensagem.cidade = posto.getCidade();
		mensagem.uf = posto.getUf();
		
		return mensagem;
	}
	
	private class Posto_JSONMessage extends JSONMessage {
		
		@JSONParameter public String id = "";
		@JSONParameter public String bandeira = "";
		@JSONParameter public String latitude = "";
		@JSONParameter public String longitude = "";
		@JSONParameter public String nome = "";
		@JSONParameter public String endereco = "";
		@JSONParameter public String cidade = "";
		@JSONParameter public String uf = "";
		
	}

}
