package com.sap.appsexperience.model.dao;

import org.json.JSONException;
import org.json.JSONObject;

import com.sap.appsexperience.model.Abastecimento;
import com.sap.appsexperience.util.AppUtil;

public class AbastecimentoDAO {

	private static AbastecimentoDAO dao = null;
	
	public static AbastecimentoDAO dao() {
		if(dao == null)
			dao = new AbastecimentoDAO();
		return dao;
	}
	
	public int realizarAbastecimento(Abastecimento abastecimento) {
		// GET para http://aurora-three.appspot.com/ondeabastecer/posto/abastecer?apelido= & instalacao= & posto= & tipo= & valorLitro= & litros= & total= &
		
		int pontos = 0;
		
		StringBuffer strGetUrl = new StringBuffer();
		strGetUrl.append("http://aurora-three.appspot.com/ondeabastecer/posto/abastecer?"); //TODO: deixar a url dinamica..
		strGetUrl.append("apelido=");
		strGetUrl.append(abastecimento.usuario.getApelido());
		strGetUrl.append("&instalacao=");
		strGetUrl.append(abastecimento.usuario.getInstallationGuid());
		strGetUrl.append("&posto=");
		strGetUrl.append(abastecimento.preco.getCombustivelPai().getPosto().getId());
		strGetUrl.append("&tipo=");
		strGetUrl.append(abastecimento.preco.getCombustivelPai().getTipo().toString());
		strGetUrl.append("&valorLitro=");
		strGetUrl.append(abastecimento.preco.getPreco());
		strGetUrl.append("&litros=");
		strGetUrl.append(abastecimento.getLitros());
		strGetUrl.append("&total=");
		strGetUrl.append(abastecimento.valorTotal);
		
		String jsonResponse = AppUtil.httpGet(strGetUrl.toString());
		
		try {
			JSONObject object = new JSONObject(jsonResponse);
			pontos = object.getInt("pontosRecebidos");
			
		} catch(JSONException ex) {
			
		}
		
		return pontos;
	}
	
}
