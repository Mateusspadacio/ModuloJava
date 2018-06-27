package com.falhasdeseguranca.unboxing;

import java.util.ArrayList;

import com.constants.Constants;
import com.metodos.padrao.MetodosAdapter;

public class FalhaUnboxingAdapter implements MetodosAdapter {

	private ArrayList<String> resultado;
	private boolean analisaMetodosUnobxing = false, tryCatch = false, throwException = false, isStart = false;
	private int contarChaves = 0;

	public FalhaUnboxingAdapter() {
		resultado = new ArrayList<String>();
	}

	@Override
	public ArrayList<String> getResultado() {
		return resultado;
	}

	@Override
	public void analisa(String linha, int num) {
		if (!retornaAnalisaUnboxing(linha)) {
			return;
		}
		
		linha = linha.replace(linha.subSequence(0, linha.indexOf(Constants.CARACTER_PARENTES_ABERTO)),
				Constants.CARACTER_VAZIO);
		String[] variaveis = linha.split(Constants.SPLIT_CLASSES_UNBOXING);
		for (String valor : variaveis) {
			valor = valor.replace(Constants.CARACTER_PARENTES_FECHADO + Constants.CARACTER_CHAVE_ABERTA,
					Constants.CARACTER_VAZIO);
			valor = valor.replace(Constants.CARACTER_PARENTES_ABERTO, Constants.CARACTER_VAZIO);

			if (valor.contains(Constants.CARACTER_PARENTES_FECHADO)) {
				valor = valor.substring(0, valor.indexOf(Constants.CARACTER_PARENTES_FECHADO));
			}

			if (valor.contains(Constants.CARACTER_VIRGULA)) {
				valor = valor.replace(valor.subSequence(valor.indexOf(Constants.CARACTER_VIRGULA), valor.length()),
						Constants.CARACTER_VAZIO);
			}

			if (!valor.isEmpty()) {
				resultado.add(valor);
			}

		}
		analisaMetodosUnobxing = true;
		if (analisaMetodosUnobxing) {
			analisandoTratamentoUnboxing(linha);
		}
	}

	private void analisandoTratamentoUnboxing(String linha) {
		if (linha.contains(Constants.CARACTER_CHAVE_ABERTA)) {
			contarChaves++;
			isStart = true;
		}

		if (linha.contains(Constants.PALAVRA_THROWS) || linha.contains(Constants.PALAVRA_THROWABLE)) {
			throwException = true;
		}

		if (linha.contains(Constants.PALAVRA_TRY + Constants.CARACTER_CHAVE_ABERTA)) {
			tryCatch = true;
		} else if (linha.contains(
				Constants.CARACTER_CHAVE_FECHADA + Constants.PALAVRA_CATCH + Constants.CARACTER_PARENTES_ABERTO)) {
			tryCatch = false;
		}

		if (linha.contains(Constants.CARACTER_CHAVE_FECHADA)) {
			contarChaves--;
		}

		if (contarChaves == 0 && isStart) {
			analisaMetodosUnobxing = false;
			throwException = false;
			isStart = false;
		} else {
			removendoDadosDesnecessarios();
			analisandoUnboxingNaoTratado(linha);
		}
	}

	private void analisandoUnboxingNaoTratado(String linha) {
		for (int i = 0; i < resultado.size(); i++) {
			String variavel = resultado.get(i);

			if (variavel.contains(Constants.CARACTER_PARENTES_FECHADO)) {
				variavel = variavel.substring(0, variavel.indexOf(Constants.CARACTER_PARENTES_FECHADO));
			}

			variavel = variavel.trim();

			if (linha.contains(variavel)
					&& (tryCatch || throwException
							|| linha.contains(variavel + Constants.CARACTER_DIFERENCA + " " + Constants.PALAVRA_NULL))
					&& !linha.contains(Constants.CARACTER_VIRGULA)) {
				resultado.remove(i);
			}
		}
	}

	private void removendoDadosDesnecessarios() {
		for (int i = 0; i < resultado.size(); i++) {
			if (resultado.get(i).contains(Constants.CARACTER_PARENTES_ABERTO)) {
				resultado.remove(i);
			}
		}
	}

	private boolean retornaAnalisaUnboxing(String linha) {
		return linha.contains(Constants.CARACTER_PARENTES_ABERTO)
				&& (linha.endsWith(Constants.CARACTER_CHAVE_ABERTA)
						|| linha.endsWith(Constants.CARACTER_PARENTES_FECHADO))
				&& (linha.contains(Constants.CLASSE_INTEGER) || linha.contains(Constants.CLASSE_FLOAT)
						|| linha.contains(Constants.CLASSE_DOUBLE) || linha.contains(Constants.CLASSE_BOOLEAN)
						|| linha.contains(Constants.CLASSE_LONG) || linha.contains(Constants.CLASSE_SHORT)
						|| linha.contains(Constants.CLASSE_BYTE) || linha.contains(Constants.CLASSE_CHARACTER))
				&& (!linha.contains(Constants.PALAVRA_ELSE_IF) && !linha.contains(Constants.PALAVRA_WHILE)
						&& !linha.contains(Constants.PALAVRA_SWITCH));
	}

}
