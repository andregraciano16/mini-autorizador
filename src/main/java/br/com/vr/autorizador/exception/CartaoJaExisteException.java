package br.com.vr.autorizador.exception;

import org.springframework.http.HttpStatus;

import br.com.vr.autorizador.dto.ErroDetailCartao;
import lombok.Getter;

@Getter
public class CartaoJaExisteException extends RuntimeException {

	private static final long serialVersionUID = -8444023544918535964L;

	private ErroDetailCartao erroDetail;
	private HttpStatus httpStatus;

	public CartaoJaExisteException() {
		super();
		httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
	}

	public CartaoJaExisteException(String cartao, String senha) {
		super();
		httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		erroDetail = ErroDetailCartao.builder().numeroCartao(cartao).senha(senha).build();
	}

	public CartaoJaExisteException(String msg) {
		super(msg);
	}

	public CartaoJaExisteException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
