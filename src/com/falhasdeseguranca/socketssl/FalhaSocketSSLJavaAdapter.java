package com.falhasdeseguranca.socketssl;

import java.util.ArrayList;

import com.constants.Constants;
import com.metodos.padrao.MetodosAdapter;

public class FalhaSocketSSLJavaAdapter implements MetodosAdapter{
	
	private ArrayList<String> resultado;
	
	protected FalhaSocketSSLJavaAdapter() {
		resultado = new ArrayList<>();
	}
	
	@Override
	public void analisa(String linha, int num) {
		if(linha.contains(Constants.PALAVRA_SOCKET) && !linha.contains(Constants.PALAVRA_SSL) 
				&& !linha.contains(Constants.PALAVRA_IMPORT) && !linha.contains(Constants.CARACTER_CHAVE_ABERTA)){
			verificaContexto(linha, num);
		}
	}
	
	private void verificaContexto(String linha, int num) {
		String split[] = linha.split(Constants.PALAVRA_PRIVATE + "|" + Constants.PALAVRA_PROTECTED + "|" + Constants.PALAVRA_PUBLIC);
		linha = split.length > 1 ? split[1].trim() : linha.trim();
		if (linha.startsWith(Constants.PALAVRA_SOCKET)) {
			resultado.add(num + Constants.CARACTER_TRACO + linha);
		}
	}
	
	@Override
	public ArrayList<String> getResultado() {
		return resultado;
	}

}
