package com.sap.appsexperience.model;

import java.util.Comparator;

import com.sap.appsexperience.model.Combustivel.ETipo;
import com.sap.appsexperience.util.LocalCache;

public class PostoComparator implements Comparator<Object> {
	
	  public int compare(Object o1, Object o2) {
      	Posto p1 = (Posto) o1;
        Posto p2 = (Posto) o2;
        Combustivel.ETipo tipo = LocalCache.getInstance().getTipoPreferencia();
        Combustivel comb1 = p1.getCombustivelByTipo(tipo);
        Combustivel comb2 = p2.getCombustivelByTipo(tipo);
        Double preco1 = (comb1.getPrecoAtual() == null ? 9999999 : comb1.getPrecoAtual().getPreco());
        Double preco2 = (comb2.getPrecoAtual() == null ? 9999999 : comb2.getPrecoAtual().getPreco());
        
        if (preco1 > preco2)
  			return 1;
  		else
  			return -1;
      }


}
