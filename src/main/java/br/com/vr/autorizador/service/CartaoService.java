package br.com.vr.autorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.autorizador.entity.Cartao;
import br.com.vr.autorizador.repository.CartaoRepository;
import br.com.vr.autorizador.request.CartaoRequest;

@Service
public class CartaoService {

	private static final BigDecimal SALDO_INICIAL = new BigDecimal(500);

	@Autowired
	private CartaoRepository cartaoRepository;

	public CartaoRequest cadastrar(CartaoRequest cartaoRequest) throws RuntimeException {
		Cartao cartao = Cartao.builder().numeroCartao(cartaoRequest.getNumeroCartao()).senha(cartaoRequest.getSenha())
				.saldo(SALDO_INICIAL).build();
		cartaoRepository.findById(cartaoRequest.getNumeroCartao()).ifPresent(cartaoBanco -> {
			throw new IllegalArgumentException("Já existe esse cartão");
		});
		cartaoRepository.saveAndFlush(cartao);
		return cartaoRequest;
	}

	public BigDecimal obterSaldo(String numeroCartao) throws Exception {
		Optional<Cartao> cartao = cartaoRepository.findById(numeroCartao);
		return cartao.orElseThrow(() -> new Exception("Cartão não existe")).getSaldo();
	}
}
