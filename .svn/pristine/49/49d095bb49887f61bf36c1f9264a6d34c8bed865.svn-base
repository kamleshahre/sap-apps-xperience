package com.sap.appsexperience;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.sap.appsexperience.model.Combustivel;
import com.sap.appsexperience.model.Combustivel.ETipo;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.PrecoHistorico;
import com.sap.appsexperience.model.Usuario;
import com.sap.appsexperience.model.Posto.EBandeira;
import com.sap.appsexperience.model.dao.CombustivelDAO;
import com.sap.appsexperience.model.dao.PostoDAO;
import com.sap.appsexperience.model.dao.UsuarioDAO;

@SuppressWarnings("serial")
public class CarregarDadosANPServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		int counter = 0;
		try {
			
            URL url = new URL("http://www.precodoscombustiveis.com.br/mapa/atualiza?swlat=-33.79&swlng=-57.60&nelat=-26.784847&nelng=-48.651123&zoom=13");
			//URL url = new URL("http://localhost:8888/atualiza.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuffer postosJson = new StringBuffer();
            
            while ((line = reader.readLine()) != null) {
                postosJson.append(line);
            }
            reader.close();
            
            JSONObject arrayOfPostos = new JSONObject(postosJson.toString());
            JSONArray chaves = arrayOfPostos.names();
            
            Usuario anpUser = UsuarioDAO.dao().getOrCreateUsuario("ANP");
            
            for(int idx = 0; idx < chaves.length(); idx++) {
            	
            	String chave = (String)chaves.get(idx);
            	JSONObject posto = arrayOfPostos.getJSONObject(chave);
            	
            	Posto postoAtualizar = PostoDAO.dao().findByANP(posto.getLong("id"));
            	if(postoAtualizar.getNome().isEmpty()) { // novo posto
            		postoAtualizar.setNome(posto.getString("nome"));
            		postoAtualizar.setEndereco(posto.getString("endereco"));
            		postoAtualizar.setLatitude(posto.getDouble("lat"));
            		postoAtualizar.setLongitude(posto.getDouble("lng"));
            		try {
            			postoAtualizar.setBandeira(EBandeira.fromFigura(posto.getString("bandeira")));
            		} catch(JSONException e) {
            			postoAtualizar.setBandeira(EBandeira.nenhuma);
            		}
            		PostoDAO.dao().save(postoAtualizar);
            		
            		Combustivel gasolina = new Combustivel(Combustivel.ETipo.gasolina, postoAtualizar);
            		Combustivel diesel = new Combustivel(Combustivel.ETipo.diesel, postoAtualizar);
            		Combustivel alcool = new Combustivel(Combustivel.ETipo.alcool, postoAtualizar);
            		
            		CombustivelDAO.dao().save(gasolina, diesel, alcool);
            		
            		postoAtualizar.addCombustivel(gasolina);
            		postoAtualizar.addCombustivel(diesel);
            		postoAtualizar.addCombustivel(alcool);
            	}
            	
            	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            	
            	Combustivel gas = postoAtualizar.getCombustivel(ETipo.gasolina);
            	Combustivel die = postoAtualizar.getCombustivel(ETipo.diesel);
            	Combustivel alc = postoAtualizar.getCombustivel(ETipo.alcool);
            	
            	// gerar o preço para cada um dos três tipos de combustivel
            	try {
	            	PrecoHistorico precoGasolina = new PrecoHistorico(gas, (Date)formatter.parse(posto.getString("dataGasolina")), posto.getDouble("Gasolina"), anpUser);
	            	gas.atualizarPreco(precoGasolina);
            	} catch(JSONException e) {
            		
            	}
            	
            	try {
            		PrecoHistorico precoDiesel = new PrecoHistorico(die, (Date)formatter.parse(posto.getString("dataDiesel")), posto.getDouble("Diesel"), anpUser);
            		die.atualizarPreco(precoDiesel);
            	} catch(JSONException e) {
            		
            	}
            	
            	try {
	            	PrecoHistorico precoAlcool = new PrecoHistorico(alc, (Date)formatter.parse(posto.getString("dataAlcool")), posto.getDouble("Alcool"), anpUser);
	            	alc.atualizarPreco(precoAlcool);
            	} catch(JSONException e) {
            		
            	}
            	
            	CombustivelDAO.dao().save(gas, die, alc);
            	PostoDAO.dao().save(postoAtualizar);
            	counter++;
            }
            
        } catch (MalformedURLException e) {
            // ...
        	e.printStackTrace();
        } catch (IOException e) {
            // ...
        	e.printStackTrace();
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().println(counter + " postos atualizados");
		
	}
}
