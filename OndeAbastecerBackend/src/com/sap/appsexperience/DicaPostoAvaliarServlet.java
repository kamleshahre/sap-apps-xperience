package com.sap.appsexperience;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.Dica;
import com.sap.appsexperience.model.dao.DicaDAO;
import com.sap.appsexperience.model.dao.PostoDAO;
import com.sap.appsexperience.resources.JSONMessage;
import com.sap.appsexperience.resources.JSONParameter;

@SuppressWarnings("serial")
public class DicaPostoAvaliarServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		//String sPosto = (String)req.getParameter("idPosto");
		
		
		//resp.setContentType("text/plain");
		//resp.getWriter().println(arrDicas.toString());
		
	}
	
	private class Dica_JSONMessage extends JSONMessage {
		
		@JSONParameter public String idPosto = "";
		
	}

}
