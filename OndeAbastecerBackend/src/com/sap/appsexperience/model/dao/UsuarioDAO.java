package com.sap.appsexperience.model.dao;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.sap.appsexperience.model.Usuario;


public class UsuarioDAO extends DAOBase {
	private static UsuarioDAO dao = null;
	static {
		ObjectifyService.register(Usuario.class);
	}
	
	public boolean exists(String apelido) {
		Usuario encontrado = ofy().find(Usuario.class, apelido);
		
		if(encontrado == null)
			return false;
		else
			return true;
	}
	
	public Usuario getOrCreateUsuario(String apelido)
    {
		Usuario found = ofy().find(Usuario.class, apelido);
        if (found == null)
            return newInstallation(apelido);
        else
            return found;
    }
	
	public void save(Usuario usuario) {
		ofy().put(usuario);
	}
	
	public void save(Usuario... usuario) {
		ofy().put(usuario);
	}
	
	public Usuario newInstallation(String apelido) {
		Usuario usuario = null;
		if(!this.exists(apelido)) {
			usuario = new Usuario(apelido);
			this.save(usuario);
		}
		
		return usuario;
	}
	
	public static UsuarioDAO dao() {
		if(dao == null)
			dao = new UsuarioDAO();
		
		return dao;
	}
}
