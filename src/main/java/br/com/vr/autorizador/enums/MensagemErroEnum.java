package br.com.vr.autorizador.enums;

import lombok.Getter;

@Getter
public enum MensagemErroEnum {

	SENHA_INVALIDA("SENHA_INVALIDA"),
	SALDO_INSUFICIENTE("SALDO_INSUFICIENTE"),
	CARTAO_INEXISTENTE("CARTAO_INEXISTENTE");
	
	private String mensagem;
	
	MensagemErroEnum(String mensagem){
		this.mensagem = mensagem;
	}
	
}
