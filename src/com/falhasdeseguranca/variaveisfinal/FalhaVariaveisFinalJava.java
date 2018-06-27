package com.falhasdeseguranca.variaveisfinal;

import java.util.ArrayList;

import com.constants.Constants;
import com.metodos.padrao.MetodosPadrao;

public class FalhaVariaveisFinalJava implements MetodosPadrao, Cloneable{

	private ArrayList<String> resultadoAnalise;
	
	public FalhaVariaveisFinalJava() {
		resultadoAnalise = new ArrayList<>();
	}
	
	@Override
	public void analisando(String linha, int num) {
		if(linha.contains(Constants.PALAVRA_PUBLIC + " " + Constants.PALAVRA_STATIC) &&
				!linha.contains(Constants.PALAVRA_FINAL) && !linha.contains(Constants.CARACTER_PARENTES_ABERTO)){
			resultadoAnalise.add(num + Constants.CARACTER_TRACO + linha);
		}
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
		FalhaVariaveisFinalJava falhaVariaveisFinal = (FalhaVariaveisFinalJava) super.clone();
		falhaVariaveisFinal.setResultadoAnalise((ArrayList<String>) getResultadoAnalise().clone());
		return falhaVariaveisFinal;
	}
	

}
