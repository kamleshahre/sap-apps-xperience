package com.sap.appsexperience.model.dao;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.sap.appsexperience.model.Abastecimento;

public class AbastecimentoDAO  extends DAOBase {
	private static AbastecimentoDAO dao = null;
	static {
		ObjectifyService.register(Abastecimento.class);
	}
	
	public static AbastecimentoDAO dao() {
		if(dao == null)
			dao = new AbastecimentoDAO();
		
		return dao;
	}
	
	public void save(Abastecimento item) {
		this.ofy().put(item);
	}
	
	public void save(Abastecimento... items) {
		this.ofy().put(items);
	}

}
