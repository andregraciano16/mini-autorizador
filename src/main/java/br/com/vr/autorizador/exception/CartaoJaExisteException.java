package br.com.vr.autorizador.exception;

public class CartaoJaExisteException extends Exception {

	private static final long serialVersionUID = -8444023544918535964L;

	public CartaoJaExisteException(String msg) {
		super(msg);
	}

	public CartaoJaExisteException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
