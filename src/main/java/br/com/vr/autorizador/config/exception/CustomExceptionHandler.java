package br.com.vr.autorizador.config.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.vr.autorizador.dto.ErroDetail;
import br.com.vr.autorizador.exception.CartaoInexistenteException;
import br.com.vr.autorizador.exception.CartaoJaExisteException;
import br.com.vr.autorizador.exception.CartaoNaoEncontradoException;
import br.com.vr.autorizador.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.exception.SenhaInvalidaException;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CartaoNaoEncontradoException.class)
	public ResponseEntity<Object> handleUCartaoNaoEncontradoException(CartaoNaoEncontradoException exception,
			WebRequest request) {

		return new ResponseEntity<>(exception.getHttpStatus());
	}

	@ExceptionHandler(CartaoJaExisteException.class)
	public ResponseEntity<Object> handleCartaoJaExisteException(CartaoJaExisteException exception,
			WebRequest request) {

		return new ResponseEntity<>(exception.getErroDetail(), exception.getHttpStatus());
	}

	@ExceptionHandler(CartaoInexistenteException.class)
	public ResponseEntity<Object> handleUserNotFoundException(CartaoInexistenteException exception,
			WebRequest request) {

		return new ResponseEntity<>(exception.getMensagemErroEnum().getMensagem(), exception.getHttpStatus());
	}

	@ExceptionHandler(SaldoInsuficienteException.class)
	public ResponseEntity<Object> handleSaldoInsuficienteException(SaldoInsuficienteException exception,
			WebRequest request) {

		return new ResponseEntity<>(exception.getMensagemErroEnum().getMensagem(), exception.getHttpStatus());
	}

	@ExceptionHandler(SenhaInvalidaException.class)
	public ResponseEntity<Object> handleSenhaInvalidaException(SenhaInvalidaException exception,
			WebRequest request) {
		return new ResponseEntity<>(exception.getMensagemErroEnum().getMensagem(), exception.getHttpStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
			WebRequest request) {
		List<ErroDetail> erros = new ArrayList<>();
		exception.getFieldErrors() .stream().forEach(e -> {
			ErroDetail detail = ErroDetail.builder().mensagem(e.getDefaultMessage()).campo(e.getField()).build();
			erros.add(detail);
		});
		return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
	}

}
