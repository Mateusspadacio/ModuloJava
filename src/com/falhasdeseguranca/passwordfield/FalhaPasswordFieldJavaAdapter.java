package com.falhasdeseguranca.passwordfield;

import java.util.ArrayList;

import com.constants.Constants;
import com.constants.FileInfos;
import com.metodos.padrao.MetodosAdapter;

public class FalhaPasswordFieldJavaAdapter implements MetodosAdapter{

	private ArrayList<String> variaveisCorrigidas;
	private ArrayList<String> resultado;
	private ArrayList<String> todosOsGetText;
	
	
	protected FalhaPasswordFieldJavaAdapter() {
		todosOsGetText = new ArrayList<>();
		variaveisCorrigidas = new ArrayList<>();
		resultado = new ArrayList<>();
	}
	
	@Override
	public void analisa(String linha, int num) {
		capturaTodosGetText(linha, num);
		verificaGettersGlobais(linha, num);
		
		if (!seraAnalisada(linha)) {
			return;
		}

		armazenaMetodosDeRetorno(linha, num);
		
		if (!analisaFalhasPasswordField(linha)) {
			return;
		}

		adicionandoFalhasNaLista(linha, num);
	}
	
	private void capturaTodosGetText(String linha, int num) {
		if (linha.contains(Constants.METODO_GET_TEXT)) {
			todosOsGetText.add(FileInfos.FILE_NAME + Constants.CARACTER_FLECHA + num + Constants.CARACTER_TRACO + linha.trim());
		}
	}
	
	private void armazenaMetodosDeRetorno(String linha, int num) {
		if (linha.contains(Constants.CARACTER_PARENTES_ABERTO) && linha.contains(Constants.CARACTER_CHAVE_ABERTA)) {
			linha = linha.replace(linha.substring(linha.indexOf(Constants.CARACTER_PARENTES_ABERTO) + 1, linha.length()), "").trim();
			linha = linha.replace(Constants.CLASSE_JPASSWORDFIELD, "");
			if (linha.startsWith(Constants.PALAVRA_PRIVATE) || linha.startsWith(Constants.PALAVRA_PUBLIC) || linha.startsWith(Constants.PALAVRA_PROTECTED)) {
				String[] metodo = linha.split(Constants.PALAVRA_PRIVATE + "|" + Constants.PALAVRA_PUBLIC + "|" + Constants.PALAVRA_PROTECTED);
				adicionaVariaveisCorrigidas(metodo[1].trim());
			} else {
				adicionaVariaveisCorrigidas(linha.trim());
			}
		}
	}
	
	private boolean analisaFalhasPasswordField(String linha) {
		if (linha.contains(Constants.METODO_GET_TEXT)) {
			return true;
		}
		
		String variavel;
		
		if (linha.trim().startsWith(Constants.CLASSE_JPASSWORDFIELD)) {
			variavel = linha.replace(Constants.CLASSE_JPASSWORDFIELD, Constants.CARACTER_VAZIO).trim();
			if (!linha.contains(Constants.CARACTER_IGUAL)) {
				corrigindoVariaveis(variavel);
				return true;
			}
			
			variavel = variavel.replace(variavel.substring(variavel.indexOf(Constants.CARACTER_IGUAL), variavel.length()), Constants.CARACTER_VAZIO).trim();
			corrigindoVariaveis(variavel);
			return true;
		}

		String[] variaveis = linha.split(Constants.SPLIT_VARIAVEIS_PASSWORDFIELD);
		if (!variaveis[1].equals(Constants.PALAVRA_NEW)) {
			variavel = variaveis[1].trim();
			corrigindoVariaveis(variavel);
			return true;
		}

		return false;
	}

	private void corrigindoVariaveis(String variavel) {
		variavel = variavel.contains(Constants.CLASSE_JPASSWORDFIELD) ? "" : variavel;
		
		if (variavel.contains(Constants.CARACTER_PONTO_VIRGULA)) {
			variavel = variavel.replace(Constants.CARACTER_PONTO_VIRGULA, Constants.CARACTER_VAZIO).trim();
			adicionaVariaveisCorrigidas(variavel);
		} else {
			adicionaVariaveisCorrigidas(variavel);
		}
	}
	
	private void adicionandoFalhasNaLista(String linha, int num) {
		if (variaveisCorrigidas.isEmpty()) {
			return;
		}
		linha = linha.trim();
		for (String variavel : variaveisCorrigidas) {
			if (variavel.isEmpty()) {
				continue;
			}
			
			if (linha.contains(variavel + Constants.METODO_GET_TEXT)) {
				adicionaLista(num + Constants.CARACTER_TRACO + variavel);
			} else if (isMetodoGetter(linha, variavel)) {
				adicionaLista(num + Constants.CARACTER_TRACO + variavel);
			}
		}
	}

	
	private void verificaGettersGlobais(String linha, int num) {
		for (int i = 0; i < todosOsGetText.size(); i++) {
			for (String variavel : variaveisCorrigidas) {
				if (!variavel.contains(Constants.CARACTER_PARENTES_ABERTO)){
					continue;
				}

			
				if (todosOsGetText.get(i).contains(variavel) && todosOsGetText.get(i).contains(Constants.METODO_GET_TEXT)) {
					adicionaLista(todosOsGetText.get(i).trim());
					todosOsGetText.remove(i);
					break;
				}
			}
		}
		
	}
	
	private boolean isMetodoGetter(String linha, String variavel) {
		variavel = variavel.replace(" ", "");
		return linha.contains(variavel + Constants.CARACTER_PARENTES_ABERTO) && linha.contains(Constants.METODO_GET_TEXT);
	}
	
	private boolean seraAnalisada(String linha) {
		if ((linha.contains(Constants.CLASSE_JPASSWORDFIELD) || linha.contains(Constants.METODO_GET_TEXT))
				&& !linha.contains(Constants.PALAVRA_IMPORT) && !linha.contains(Constants.CARACTER_PARENTESES + Constants.CLASSE_JPASSWORDFIELD
						+ Constants.CARACTER_PARENTESES)) {
			return true;
		}
		return false;
	}
	
	private void adicionaLista(String value) {
		resultado.add(value);
	}
	
	private void adicionaVariaveisCorrigidas(String value) {
		variaveisCorrigidas.add(value);
	}
	
	@Override
	public ArrayList<String> getResultado() {
		return resultado;
	}
}
