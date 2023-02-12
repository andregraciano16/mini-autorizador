package br.com.vr.autorizador.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.autorizador.request.CartaoRequest;
import br.com.vr.autorizador.service.CartaoService;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

	@Autowired
	private CartaoService cartaoService;

	@PostMapping
	public ResponseEntity<CartaoRequest> cadastrar(@RequestBody CartaoRequest cartaoRequest) {
		try {
			cartaoService.cadastrar(cartaoRequest);
			return ResponseEntity.status(HttpStatus.CREATED).body(cartaoRequest);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(cartaoRequest);
		}
	}

	@GetMapping("/{numeroCartao}")
	public ResponseEntity<BigDecimal> obterSaldo(@PathVariable String numeroCartao) {
		BigDecimal obterSaldo = cartaoService.obterSaldo(numeroCartao);
		return ResponseEntity.ok(obterSaldo);
	}

}
