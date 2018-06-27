package com.falhasdeseguranca.sqlinjector;

import java.util.ArrayList;

import com.constants.Constants;
import com.metodos.padrao.MetodosAdapter;

public class FalhaSQLInjectorJavaAdapter implements MetodosAdapter{
	
	private ArrayList<String> resultado;
	private boolean isInstrucao = false;
	
	protected FalhaSQLInjectorJavaAdapter() {
		resultado = new ArrayList<>();
	}
	
	@Override
	public void analisa(String linha, int num) {
		linha = linha.toLowerCase();
		if(palavrasReservadasSQL(linha) || isInstrucao){
			
			isInstrucao = true;
			
			if (verificaConcatenacao(linha)) {
				resultado.add(num + Constants.CARACTER_TRACO + linha);
			}
			
			if (verificaFimInstrucao(linha)) {
				isInstrucao = false;
			}
			
		}
	}
	
	private boolean verificaFimInstrucao(String linha) {
		return linha.endsWith(Constants.CARACTER_PONTO_VIRGULA);
	}
	
	private boolean verificaConcatenacao(String linha) {
		return linha.contains(Constants.CARACTER_CONCATENACAO);
	}
	
	private boolean palavrasReservadasSQL(String linha) {
		return (linha.contains(Constants.SQL_SELECT) || linha.contains(Constants.SQL_UPDATE) || 
				linha.contains(Constants.SQL_INSERT) || linha.contains(Constants.SQL_DELETE) || 
				linha.contains(Constants.SQL_BEGIN) || linha.contains(Constants.SQL_CALL));
	}
	
	@Override
	public ArrayList<String> getResultado() {
		return resultado;
	}

}
