package com.sap.appsexperience.model.dao;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.sap.appsexperience.model.ItemReputacao;

public class ItemReputacaoDAO  extends DAOBase {
	private static ItemReputacaoDAO dao = null;
	static {
		ObjectifyService.register(ItemReputacao.class);
	}
	
	public static ItemReputacaoDAO dao() {
		if(dao == null)
			dao = new ItemReputacaoDAO();
		
		return dao;
	}
	
	public void save(ItemReputacao... items) {
		this.ofy().put(items);
	}

}
