package br.com.vr.autorizador.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CartaoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1969609561899227357L;
	private HttpStatus httpStatus;

	public CartaoNaoEncontradoException() {
		super();
		httpStatus = HttpStatus.NOT_FOUND;
	}
	
	public CartaoNaoEncontradoException(String msg) {
		super(msg);
	}

	public CartaoNaoEncontradoException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
