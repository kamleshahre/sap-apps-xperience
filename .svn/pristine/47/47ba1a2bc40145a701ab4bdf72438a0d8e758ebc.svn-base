package com.sap.appsexperience.model.dao;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.sap.appsexperience.model.Combustivel;
import com.sap.appsexperience.model.Combustivel.ETipo;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.Posto.EBandeira;
import com.sap.appsexperience.model.PrecoHistorico;
import com.sap.appsexperience.resources.JSONMessage;
import com.sap.appsexperience.resources.JSONParameter;
import com.sap.appsexperience.util.AppUtil;
import com.sap.appsexperience.util.LocalCache;

public class PostoDAO {

	private static PostoDAO dao = null;
	
	public static PostoDAO dao() {
		if(dao == null)
			dao = new PostoDAO();
		return dao;
	}
	
	public List<Posto> getPostosById(double latitude, double longitude, String ids) {
		// GET para http://aurora-three.appspot.com/ondeabastecer/posto/procurar?lat= & lng= & postos=
		ArrayList<Posto> listaPostos = new ArrayList<Posto>();
		
		StringBuffer strGetUrl = new StringBuffer();
		strGetUrl.append("http://aurora-three.appspot.com/ondeabastecer/posto/porid?"); //TODO: deixar a url dinamica..
		strGetUrl.append("lat=");
		strGetUrl.append(String.valueOf(latitude));
		strGetUrl.append("&lng=");
		strGetUrl.append(String.valueOf(longitude));
		strGetUrl.append("&postos=");
		strGetUrl.append(ids);
		
		String jsonResponse = AppUtil.httpGet(strGetUrl.toString());
		try {
			JSONArray arrListaJson = new JSONArray(jsonResponse);
			
			for(int idx = 0; idx < arrListaJson.length(); idx++) {
				Posto_JSONMessage postoJson = new Posto_JSONMessage();
				postoJson.fromJSON(arrListaJson.getString(idx));
				
				Posto posto = fromJsonToPosto(postoJson);
				
				for(int idxComb = 0; idxComb < postoJson.combustiveis.length(); idxComb++) {
					Combustivel_JSONMessage combustivelJson = new Combustivel_JSONMessage();
					combustivelJson.fromJSON(postoJson.combustiveis.getString(idxComb));
					
					Combustivel combustivel = fromJsonToCombustivel(posto, combustivelJson);
					posto.addCombustivelNoPosto(combustivel);
				}
				
				listaPostos.add(posto);
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaPostos;
		
	}
	
	public List<Posto> getPostoNearby(double latitude, double longitude, double raioEmKm) {
		// GET para http://aurora-three.appspot.com/ondeabastecer/posto/procurar?lat= & lng= & raio=
		ArrayList<Posto> listaPostos = new ArrayList<Posto>();
		
		StringBuffer strGetUrl = new StringBuffer();
		strGetUrl.append("http://aurora-three.appspot.com/ondeabastecer/posto/procurar?"); //TODO: deixar a url dinamica..
		strGetUrl.append("lat=");
		strGetUrl.append(String.valueOf(latitude));
		strGetUrl.append("&lng=");
		strGetUrl.append(String.valueOf(longitude));
		strGetUrl.append("&raio=");
		strGetUrl.append(String.valueOf(raioEmKm));
		
		String jsonResponse = AppUtil.httpGet(strGetUrl.toString());
		try {
			JSONArray arrListaJson = new JSONArray(jsonResponse);
			
			for(int idx = 0; idx < arrListaJson.length(); idx++) {
				Posto_JSONMessage postoJson = new Posto_JSONMessage();
				postoJson.fromJSON(arrListaJson.getString(idx));
				
				Posto posto = fromJsonToPosto(postoJson);
				
				for(int idxComb = 0; idxComb < postoJson.combustiveis.length(); idxComb++) {
					Combustivel_JSONMessage combustivelJson = new Combustivel_JSONMessage();
					combustivelJson.fromJSON(postoJson.combustiveis.getString(idxComb));
					
					Combustivel combustivel = fromJsonToCombustivel(posto, combustivelJson);
					posto.addCombustivelNoPosto(combustivel);
				}
				
				listaPostos.add(posto);
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LocalCache.getInstance().setPostos(listaPostos);
		return listaPostos;
	}
	
	private Combustivel fromJsonToCombustivel(Posto posto, Combustivel_JSONMessage mensagem) {
		Combustivel combBackend = new Combustivel(Long.valueOf(mensagem.id), ETipo.valueOf(mensagem.tipo), posto);
		
		try {
			if(!mensagem.idPrecoAtual.isEmpty()) { 
				PrecoHistorico precoBackend = new PrecoHistorico(Long.valueOf(mensagem.idPrecoAtual), combBackend, mensagem.usuarioPrecoAtual);
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			
				precoBackend.setDataAtualizacao((Date)formatter.parse(mensagem.dataPrecoAtual));
			
				precoBackend.setPreco(Double.valueOf(mensagem.valorPrecoAtual));
				combBackend.setPrecoAtual(precoBackend);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return combBackend;
	}

	private Posto fromJsonToPosto(Posto_JSONMessage mensagem) {
		Posto postoBackend = new Posto();
		postoBackend.setId(Long.valueOf(mensagem.id));
		postoBackend.setBandeira(EBandeira.valueOf(mensagem.bandeira));
		postoBackend.setLatitude(Double.valueOf(mensagem.latitude));
		postoBackend.setLongitude(Double.valueOf(mensagem.longitude));
		postoBackend.setNome(mensagem.nome);
		postoBackend.setEndereco(mensagem.endereco);
		postoBackend.ultimaDistancia = Double.valueOf(mensagem.distancia);
		return postoBackend;
	}

	private class Posto_JSONMessage extends JSONMessage {
		
		@JSONParameter public String id = "";
		@JSONParameter public String bandeira = "";
		@JSONParameter public String latitude = "";
		@JSONParameter public String longitude = "";
		@JSONParameter public String nome = "";
		@JSONParameter public String endereco = "";
		@JSONParameter public JSONArray combustiveis = new JSONArray();
		@JSONParameter public String distancia = "";
		public List<Combustivel_JSONMessage> combustivelList = new ArrayList<Combustivel_JSONMessage>();
		
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
