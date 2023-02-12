package br.com.vr.autorizador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.autorizador.request.TransacaoRequest;
import br.com.vr.autorizador.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

	@Autowired
	private TransacaoService transacaoService;

	@PostMapping
	public ResponseEntity<?> realizarTransacao(@RequestBody TransacaoRequest transacaoRequest) {
		transacaoService.realizarTransacao(transacaoRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("OK");
	}

}
