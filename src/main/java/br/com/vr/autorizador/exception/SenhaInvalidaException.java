package br.com.vr.autorizador.exception;

public class SenhaInvalidaException extends Exception {

	private static final long serialVersionUID = 4108335237572517796L;

	public SenhaInvalidaException(String msg) {
		super(msg);
	}

	public SenhaInvalidaException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
