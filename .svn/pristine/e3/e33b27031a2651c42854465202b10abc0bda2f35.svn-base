package com.sap.appsexperience;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Currency;

import com.sap.appsexperience.model.Abastecimento;
import com.sap.appsexperience.model.Combustivel;
import com.sap.appsexperience.model.Combustivel.ETipo;
import com.sap.appsexperience.model.dao.AbastecimentoDAO;
import com.sap.appsexperience.model.Posto;
import com.sap.appsexperience.model.PrecoHistorico;
import com.sap.appsexperience.util.LocalCache;
import com.sap.appsexperience.util.MenuHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AbastecerActivity extends Activity {
	
	private Posto postoAtual;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.abastecer);
		
		this.postoAtual = (Posto) this.getIntent().getExtras().get("posto");
		atualizaPrecoSelecionado(LocalCache.getInstance().getTipoPreferencia());
		
		
		setupUI();
	}
	
	private void atualizaPrecoSelecionado(ETipo tipoCombustivel) {
		// TODO Auto-generated method stub
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);
		
		if (this.postoAtual != null) {
			EditText txtValorLitro = (EditText)findViewById(R.id.txtValorLitro);
			Combustivel comb = this.postoAtual.getCombustivelByTipo(tipoCombustivel);
		
			Double preco = ( comb.getPrecoAtual() != null ? comb.getPrecoAtual().getPreco() : 0);
			txtValorLitro.setText(nf.format(preco));
			recalcularLitros();
		}
		
	}

	private void recalcularLitros() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);
		EditText txtLitros = (EditText)findViewById(R.id.txtLitros);
		
		
		
		try {
			Double valorTotal = nf.parse((((EditText)findViewById(R.id.txtValorTotal)).getText().toString())).doubleValue();
			Double valorLitro = nf.parse(((EditText)findViewById(R.id.txtValorLitro)).getText().toString()).doubleValue();
			Double litros = valorTotal / valorLitro;
			
			txtLitros.setText(nf.format(litros));
		} catch(Exception e) {
			
		}
		
	}

	private void setupUI() {
	    RadioButton rbGsl = (RadioButton) findViewById(R.id.rbGsl);
	    RadioButton rbAlc = (RadioButton) findViewById(R.id.rbAlc);
	    RadioButton rbDie = (RadioButton) findViewById(R.id.rbDie);
	    rbGsl.setButtonDrawable(R.drawable.bt_gas);
	    rbAlc.setButtonDrawable(R.drawable.bt_alc);
	    rbDie.setButtonDrawable(R.drawable.bt_die);
	    
	    

	    RadioGroup rg = (RadioGroup) findViewById(R.id.topBtns);
	    
	    switch(LocalCache.getInstance().getTipoPreferencia()) {
	    	case gasolina:
	    		rg.check(R.id.rbGsl);
	    		break;
	    	case alcool:
	    		rg.check(R.id.rbAlc);
	    		break;
	    	case diesel:
	    		rg.check(R.id.rbDie);
	    		break;
	    }
	    
	    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
	        public void onCheckedChanged(RadioGroup group, final int checkedId) {
	            switch (checkedId) {
	            case R.id.rbGsl:
	            	atualizaPrecoSelecionado(ETipo.gasolina);
	                break;
	            case R.id.rbAlc:
	            	atualizaPrecoSelecionado(ETipo.alcool);
	                break;
	            case R.id.rbDie:
	            	atualizaPrecoSelecionado(ETipo.diesel);
	            	break;
	            }
	        }
	    });
	    
	    
	    View.OnKeyListener doneButton = new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
		              recalcularLitros();
		              return true;
		         }
		       return false;
			}
	    };
	    
	    EditText txtValorLitro = (EditText)findViewById(R.id.txtValorLitro);
	    txtValorLitro.setOnKeyListener(doneButton);
	    
	    EditText txtValorTotal = (EditText)findViewById(R.id.txtValorTotal);
	    txtValorTotal.setOnKeyListener(doneButton);
	    
	}
	
	public void abasteci(View view) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);
		
		recalcularLitros();
		RadioButton rbGsl = (RadioButton) findViewById(R.id.rbGsl);
	    RadioButton rbAlc = (RadioButton) findViewById(R.id.rbAlc);
	    RadioButton rbDie = (RadioButton) findViewById(R.id.rbDie);
		
		Combustivel comb = null;
		PrecoHistorico preco = null;
		
		if(rbGsl.isChecked()) {
			comb = this.postoAtual.getCombustivelByTipo(ETipo.gasolina);
		} else if(rbAlc.isChecked()) {
			comb = this.postoAtual.getCombustivelByTipo(ETipo.alcool);
		} else if(rbDie.isChecked()) {
			comb = this.postoAtual.getCombustivelByTipo(ETipo.diesel);
		}
		
		if(comb != null) {
			try {
				Double valorLitro = nf.parse((((EditText)findViewById(R.id.txtValorLitro)).getText().toString())).doubleValue();
				
				Double valorTotal = nf.parse(((EditText)findViewById(R.id.txtValorTotal)).getText().toString()).doubleValue();
				Double litros = nf.parse(((EditText)findViewById(R.id.txtLitros)).getText().toString()).doubleValue();
				Double precoAnterior = ( comb.getPrecoAtual() != null ? comb.getPrecoAtual().getPreco() : 0);
			
				if(valorLitro != precoAnterior) {
					preco = new PrecoHistorico(comb, Calendar.getInstance().getTime(), valorLitro, LocalCache.getInstance().getUsuarioAtual().getApelido());
				} else {
					preco = comb.getPrecoAtual();
				}
				
				Abastecimento abastecimento = new Abastecimento(preco,  LocalCache.getInstance().getUsuarioAtual(), litros, valorTotal);
				AbastecimentoDAO.dao().realizarAbastecimento(abastecimento);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.finish();
		
	}
}
