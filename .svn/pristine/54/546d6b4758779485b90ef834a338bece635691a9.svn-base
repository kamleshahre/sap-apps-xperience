package com.sap.appsexperience.model.dao;

import org.json.JSONException;
import org.json.JSONObject;

import com.sap.appsexperience.model.Usuario;
import com.sap.appsexperience.resources.JSONMessage;
import com.sap.appsexperience.resources.JSONParameter;
import com.sap.appsexperience.util.AppUtil;

public class UsuarioDAO {

	private static UsuarioDAO dao = null;
	
	public static UsuarioDAO dao() {
		if(dao == null)
			dao = new UsuarioDAO();
		return dao;
	}
	
	public Usuario novaInstalacao(String apelido) {
		// GET para http://aurora-three.appspot.com/ondeabastecer/novaInstalacao
		
		StringBuffer strGetUrl = new StringBuffer();
		strGetUrl.append("http://aurora-three.appspot.com/ondeabastecer/novaInstalacao?"); //TODO: deixar a url dinamica..
		strGetUrl.append("apelido=");
		strGetUrl.append(apelido);
		
		String jsonResponse = AppUtil.httpGet(strGetUrl.toString());
		
		JSONObject respostaJson;
		try {
			respostaJson = new JSONObject(jsonResponse);
			boolean erro = respostaJson.getBoolean("erro");
			
			if(!erro) {
				String instalacao = respostaJson.getString("installation");
				Usuario novoUsuario = new Usuario();
				novoUsuario.setInstallationGuid(instalacao);
				novoUsuario.setApelido(apelido);
				novoUsuario.setReputacao(0);
				return novoUsuario;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public Long getReputacao(String apelido) {
		// GET para http://aurora-three.appspot.com/ondeabastecer/usuario/reputacao?apelido= 
		
		StringBuffer strGetUrl = new StringBuffer();
		strGetUrl.append("http://aurora-three.appspot.com/ondeabastecer/usuario/reputacao?"); //TODO: deixar a url dinamica..
		strGetUrl.append("apelido=");
		strGetUrl.append(apelido);
		
		String jsonResponse = AppUtil.httpGet(strGetUrl.toString());
		
		Usuario_JSONMessage mensagem = new Usuario_JSONMessage();
		mensagem.fromJSON(jsonResponse);
		
		Long reputacao = 0L;
		
		if(!mensagem.reputacao.isEmpty()) {
			try {
				reputacao = Long.valueOf(mensagem.reputacao);
			} catch(Exception e) {
				
			}
		}
		
		return reputacao;
	}
	

	private class Usuario_JSONMessage extends JSONMessage {
		
		@JSONParameter public String apelido = "";
		@JSONParameter public String reputacao = "";
		
	}
	
}
