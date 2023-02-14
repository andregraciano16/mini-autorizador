package br.com.vr.autorizador.service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.autorizador.entity.Cartao;
import br.com.vr.autorizador.exception.CartaoJaExisteException;
import br.com.vr.autorizador.exception.CartaoNaoEncontradoException;
import br.com.vr.autorizador.repository.CartaoRepository;
import br.com.vr.autorizador.request.CartaoRequest;

@Service
public class CartaoService {

	private static final BigDecimal SALDO_INICIAL = new BigDecimal(500);

	@Autowired
	private CartaoRepository cartaoRepository;

	public Cartao cadastrar(CartaoRequest cartaoRequest) throws RuntimeException {
		try {
			cartaoRepository.findById(cartaoRequest.getNumeroCartao()).orElseThrow();
			throw new CartaoJaExisteException(cartaoRequest.getNumeroCartao(), cartaoRequest.getSenha());
		} catch (NoSuchElementException e) {
			Cartao cartao = Cartao.builder().numeroCartao(cartaoRequest.getNumeroCartao())
					.senha(cartaoRequest.getSenha()).saldo(SALDO_INICIAL).build();
			return cartaoRepository.saveAndFlush(cartao);
		}
	}

	public Cartao findByNumeroCartao(String numeroCartao) {
		Optional<Cartao> cartao = cartaoRepository.findById(numeroCartao);
		return cartao.orElseThrow(() -> new CartaoNaoEncontradoException());
	}

	public BigDecimal obterSaldo(String numeroCartao) {
		return findByNumeroCartao(numeroCartao).getSaldo();
	}

	public Cartao atualizarSaldo(Cartao cartao, BigDecimal valorTransacao) {
		cartao.setSaldo(cartao.getSaldo().subtract(valorTransacao));
		return cartaoRepository.saveAndFlush(cartao);
	}
}
