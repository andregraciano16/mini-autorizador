package br.com.vr.autorizador.exception;

import org.springframework.http.HttpStatus;

import br.com.vr.autorizador.enums.MensagemErroEnum;
import lombok.Getter;

@Getter
public class SaldoInsuficienteException extends RuntimeException {

	private static final long serialVersionUID = -8717001500104914717L;

	private HttpStatus httpStatus;
	private MensagemErroEnum mensagemErroEnum;
	
	public SaldoInsuficienteException() {
		super();
		httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		mensagemErroEnum = MensagemErroEnum.SALDO_INSUFICIENTE;
	}
	
	public SaldoInsuficienteException(String msg) {
		super(msg);
	}

	public SaldoInsuficienteException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
