package com.sap.appsexperience.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.sap.appsexperience.model.Combustivel;

public class CombustivelDAO extends DAOBase {
	
	private static CombustivelDAO dao = null;
	static {
		ObjectifyService.register(Combustivel.class);
	}
	
	public static CombustivelDAO dao() {
		if(dao == null)
			dao = new CombustivelDAO();
		
		return dao;
	}
	
	public Collection<Combustivel> getList(Collection<Key<Combustivel>> ids) {
		Map<Key<Combustivel>, Combustivel> fetched = ofy().get(ids);
		
		return new ArrayList<Combustivel>(fetched.values());
	}
	
	public Combustivel getCombustivel(long id)
    {
		Combustivel found = ofy().find(Combustivel.class, id);
            return found;
    }
	
	public void save(Combustivel combustivel) {
		ofy().put(combustivel);
	}
	
	public void save(Combustivel... combustivel) {
		ofy().put(combustivel);
	}

}
