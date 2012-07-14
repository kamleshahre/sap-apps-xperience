package com.sap.appsexperience.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.sap.appsexperience.model.Posto;

public class PostoDAO  extends DAOBase {
	private static PostoDAO dao = null;
	static {
		ObjectifyService.register(Posto.class);
	}
	
	public static PostoDAO dao() {
		if(dao == null)
			dao = new PostoDAO();
		
		return dao;
	}
	
	public Posto getOrCreatePosto(long id)
    {
        Posto found = ofy().find(Posto.class, id);
        if (found == null)
            return new Posto(id);
        else
            return found;
    }
	
	public void save(Posto posto) {
		ofy().put(posto);
	}

	public List<Posto> findByLatLong(double minLat,
			double maxLat, double minLng, double maxLng) {
		
		List<Posto> filtroPorLng = new ArrayList<Posto>();
		List<Posto> postos = ofy().query(Posto.class)
			.filter("latitude >=", minLat)
			.filter("latitude <=", maxLat).list(); // no brasil tende a variar mais a latitude e o App engine não suporta mais de um parametro com inequalidade
		//.filter("longitude >=", minLng)
		//.filter("longitude <=", maxLng)
		
		for(Posto posto : postos) {
			if(posto.getLongitude() >= minLng && posto.getLongitude() <= maxLng)
				filtroPorLng.add(posto);
		}
		
		return filtroPorLng;

	}

	public Posto findByANP(long idOrigem) {
		Posto found = ofy().query(Posto.class).filter("idOrigem", idOrigem).get();
		if (found == null) {
            found = new Posto();
            found.setIdOrigem(idOrigem);
		}
        return found;
	}


}
