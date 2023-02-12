package br.com.vr.autorizador.exception;

public class CartaoNaoEncontradoException extends Exception {

	private static final long serialVersionUID = 1969609561899227357L;

	public CartaoNaoEncontradoException(String msg) {
		super(msg);
	}

	public CartaoNaoEncontradoException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
