package br.com.vr.autorizador.exception;

import org.springframework.http.HttpStatus;

import br.com.vr.autorizador.enums.MensagemErroEnum;
import lombok.Getter;

@Getter
public class SenhaInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 4108335237572517796L;

	private HttpStatus httpStatus;
	private MensagemErroEnum mensagemErroEnum;
	
	public SenhaInvalidaException() {
		httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		mensagemErroEnum = MensagemErroEnum.SENHA_INVALIDA;
	}
	
	public SenhaInvalidaException(String msg) {
		super(msg);
	}

	public SenhaInvalidaException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
