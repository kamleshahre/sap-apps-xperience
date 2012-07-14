package com.sap.appsexperience.model.dao;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.sap.appsexperience.model.Dica;

public class DicaDAO  extends DAOBase {
	private static DicaDAO dao = null;
	static {
		ObjectifyService.register(Dica.class);
	}
	
	public static DicaDAO dao() {
		if(dao == null)
			dao = new DicaDAO();
		
		return dao;
	}
	
	public void save(Dica items) {
		this.ofy().put(items);
	}

}
