package com.falhasdeseguranca.sqlinjector;

import java.util.ArrayList;

import com.constants.Constants;
import com.metodos.padrao.MetodosAdapter;
import com.metodos.padrao.MetodosPadrao;

public class FalhaSQLInjectorJava implements MetodosPadrao, Cloneable{

	private ArrayList<String> resultadoAnalise; 
	private MetodosAdapter adapter;
	
	public FalhaSQLInjectorJava() {
		resultadoAnalise = new ArrayList<>();
		adapter = new FalhaSQLInjectorJavaAdapter();
	}
	
	@Override
	public void analisando(String linha, int num) {
		adapter.analisa(linha, num);
		resultadoAnalise = adapter.getResultado();
	}
	
	@Override
	public ArrayList<String> getResultadoAnalise() {
		return resultadoAnalise;
	}	

	@Override
	public void clearSession() {
		resultadoAnalise.clear();
	}
	
	private void setResultadoAnalise(ArrayList<String> resultadoAnalise) {
		this.resultadoAnalise = resultadoAnalise;
	}
	
	@Override
	public Object clonar() {
		try {
			return clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		FalhaSQLInjectorJava falhaSQLInjector = (FalhaSQLInjectorJava) super.clone();
		ArrayList<String> listaClone = (ArrayList<String>) getResultadoAnalise().clone();
		falhaSQLInjector.setResultadoAnalise(listaClone);
		return falhaSQLInjector;
	}

	
}
