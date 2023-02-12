package br.com.vr.autorizador.exception;

import org.springframework.http.HttpStatus;

import br.com.vr.autorizador.enums.MensagemErroEnum;
import lombok.Getter;

@Getter
public class CartaoInexistenteException extends RuntimeException {

	private static final long serialVersionUID = -1131512295358861747L;
	
	private HttpStatus httpStatus;
	private MensagemErroEnum mensagemErroEnum;

	public CartaoInexistenteException() {
		super();
		httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		mensagemErroEnum = MensagemErroEnum.CARTAO_INEXISTENTE;
	}
	
	public CartaoInexistenteException(String msg) {
		super(msg);
	}

	public CartaoInexistenteException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
