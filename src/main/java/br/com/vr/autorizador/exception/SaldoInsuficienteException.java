package br.com.vr.autorizador.exception;

public class SaldoInsuficienteException extends Exception {

	private static final long serialVersionUID = -8717001500104914717L;

	public SaldoInsuficienteException(String msg) {
		super(msg);
	}

	public SaldoInsuficienteException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
