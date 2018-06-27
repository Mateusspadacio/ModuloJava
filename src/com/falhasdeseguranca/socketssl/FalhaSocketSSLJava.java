package com.falhasdeseguranca.socketssl;

import java.util.ArrayList;

import com.constants.Constants;
import com.metodos.padrao.MetodosAdapter;
import com.metodos.padrao.MetodosPadrao;

public class FalhaSocketSSLJava implements MetodosPadrao, Cloneable {
	private ArrayList<String> resultadoAnalise;
	private MetodosAdapter adapter;

	public FalhaSocketSSLJava() {
		resultadoAnalise = new ArrayList<String>();
		adapter = new FalhaSocketSSLJavaAdapter();
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
		FalhaSocketSSLJava falhaSocketSSL = (FalhaSocketSSLJava) super.clone();
		falhaSocketSSL.setResultadoAnalise((ArrayList<String>) getResultadoAnalise().clone());
		return falhaSocketSSL;
	}

}
