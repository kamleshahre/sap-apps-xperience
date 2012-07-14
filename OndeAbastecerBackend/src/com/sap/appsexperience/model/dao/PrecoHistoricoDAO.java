package com.sap.appsexperience.model.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.sap.appsexperience.model.PrecoHistorico;

public class PrecoHistoricoDAO extends DAOBase {
	
	private static PrecoHistoricoDAO dao = null;
	static {
		ObjectifyService.register(PrecoHistorico.class);
	}
	
	public PrecoHistorico get(Key<PrecoHistorico> chave) {
		return ofy().get(chave);
	}
	
	public static PrecoHistoricoDAO dao() {
		if(dao == null)
			dao = new PrecoHistoricoDAO();
		
		return dao;
	}
	
	public void save(PrecoHistorico preco) {
		ofy().put(preco);
	}

}
