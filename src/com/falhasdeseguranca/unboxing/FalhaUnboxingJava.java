package com.falhasdeseguranca.unboxing;

import java.util.ArrayList;

import com.metodos.padrao.MetodosAdapter;
import com.metodos.padrao.MetodosPadrao;

public class FalhaUnboxingJava implements MetodosPadrao, Cloneable{

	private ArrayList<String> resultadoAnalise;
	private MetodosAdapter adapter;
	
	public FalhaUnboxingJava() {
		resultadoAnalise = new ArrayList<>();
		adapter = new FalhaUnboxingAdapter();
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

	private void setResultadoAnalise(ArrayList<String> resultadoAnalise) {
		this.resultadoAnalise = resultadoAnalise;
	}
	
	@Override
	public void clearSession() {
		resultadoAnalise.clear();
		
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
		FalhaUnboxingJava falhaUnboxing = (FalhaUnboxingJava) super.clone();
		falhaUnboxing.setResultadoAnalise((ArrayList<String>) getResultadoAnalise().clone());
		return falhaUnboxing;
	}
	
	
}
