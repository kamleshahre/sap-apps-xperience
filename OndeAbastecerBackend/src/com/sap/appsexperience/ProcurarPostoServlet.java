package com.sap.appsexperience;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.sap.appsexperience.model.Combustivel;
import com.sap.appsexperience.model.Combustivel.ETipo;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.Usuario;
import com.sap.appsexperience.model.dao.PostoDAO;
import com.sap.appsexperience.model.dao.UsuarioDAO;
import com.sap.appsexperience.resources.JSONMessage;
import com.sap.appsexperience.resources.JSONParameter;

@SuppressWarnings("serial")
public class ProcurarPostoServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String sLat = (String)req.getParameter("lat");
		String sLng = (String)req.getParameter("lng");
		String sRaio = (String)req.getParameter("raio");

		Double lat = Double.valueOf(sLat); // latitude atual
		Double lng = Double.valueOf(sLng); // longitude atual
		Float raio = Float.valueOf(sRaio); // distancia em km
		
		double graus = raio / 111;
		double minLat = lat - graus;
		double maxLat = lat + graus;
		
		double PI_180 = Math.PI / 180;
		graus /= Math.cos(lat * PI_180);
		double minLng = lng - graus;
		double maxLng = lng + graus;
		
		
		List<Posto> postos = PostoDAO.dao().findByLatLong(minLat, maxLat, minLng, maxLng);
		
		JSONArray arrPostosSelecionados = new JSONArray();
		for(Posto posto : postos) {
			Combustivel gasolina = posto.getCombustivel(ETipo.gasolina); // deveria ser dinamico
			Combustivel diesel = posto.getCombustivel(ETipo.diesel);
			Combustivel alcool = posto.getCombustivel(ETipo.alcool);
			
			JSONArray combustivelNoPosto = new JSONArray();
			combustivelNoPosto.put(combustivelToJson(gasolina).getJSON());
			combustivelNoPosto.put(combustivelToJson(diesel).getJSON());
			combustivelNoPosto.put(combustivelToJson(alcool).getJSON());
			
			Posto_JSONMessage postoJson = postoToJson(posto);
			postoJson.combustiveis = combustivelNoPosto;
			arrPostosSelecionados.put(postoJson.getJSON());
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().println(arrPostosSelecionados.toString());
	}
	
	private Posto_JSONMessage postoToJson(Posto posto) {
		Posto_JSONMessage mensagem = new Posto_JSONMessage();
		mensagem.id = posto.getId().toString();
		mensagem.bandeira = posto.getBandeira().toString();
		mensagem.latitude = String.valueOf(posto.getLatitude());
		mensagem.longitude = String.valueOf(posto.getLongitude());
		mensagem.nome = posto.getNome();
		mensagem.endereco = posto.getEndereco();
		
		return mensagem;
	}

	private JSONMessage combustivelToJson(Combustivel combustivel) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		Combustivel_JSONMessage mensagem = new Combustivel_JSONMessage();
		mensagem.id = combustivel.getId().toString();
		mensagem.tipo = combustivel.getTipo().toString();
		if(combustivel.getPrecoAtual() != null) { // pode não existir o combustível no posto em questão
			mensagem.idPrecoAtual = combustivel.getPrecoAtual().getId().toString();
			mensagem.valorPrecoAtual = String.valueOf(combustivel.getPrecoAtual().getPreco());
			mensagem.dataPrecoAtual = formatter.format(combustivel.getPrecoAtual().getDataAtualizacao());
			mensagem.usuarioPrecoAtual = combustivel.getPrecoAtual().getResponsavel().getApelido();
		}
		
		return mensagem;
	}

	private class Posto_JSONMessage extends JSONMessage {
		
		@JSONParameter public String id = "";
		@JSONParameter public String bandeira = "";
		@JSONParameter public String latitude = "";
		@JSONParameter public String longitude = "";
		@JSONParameter public String nome = "";
		@JSONParameter public String endereco = "";
		@JSONParameter public JSONArray combustiveis = new JSONArray();
		
	}
	
	private class Combustivel_JSONMessage extends JSONMessage {
		
		@JSONParameter public String id = "";
		@JSONParameter public String tipo = "";
		@JSONParameter public String idPrecoAtual = "";
		@JSONParameter public String valorPrecoAtual = "";
		@JSONParameter public String dataPrecoAtual = "";
		@JSONParameter public String usuarioPrecoAtual = "";
		
	}

}
