package br.com.vr.autorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.autorizador.entity.Cartao;
import br.com.vr.autorizador.entity.Transacao;
import br.com.vr.autorizador.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.exception.SenhaInvalidaException;
import br.com.vr.autorizador.repository.TransacaoRepository;
import br.com.vr.autorizador.request.TransacaoRequest;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository transacaoRepository;

	@Autowired
	private CartaoService cartaoService;

	public TransacaoRequest realizarTransacao(TransacaoRequest transacaoRequest) {

		Cartao cartao = cartaoService.findByNumeroCartao(transacaoRequest.getNumeroCartao());
        validarTransacao(cartao, transacaoRequest);
		Transacao transacao = Transacao.builder().valor(transacaoRequest.getValor()).cartao(cartao).build();
		transacaoRepository.save(transacao);
		cartaoService.atualizarSaldo(cartao, transacaoRequest.getValor());
		return transacaoRequest;
	}

	private void validarTransacao(Cartao cartao, TransacaoRequest transacaoRequest) {
		validarSenha(cartao, transacaoRequest.getSenhaCartao());
		validarSaldo(cartao, transacaoRequest.getValor());
	}
	
	private void validarSaldo(Cartao cartao, BigDecimal saldoInformado) {
		Optional<Cartao> cartaoValido = Optional.of(cartao)
				.filter(cartaoBanco -> cartaoBanco.getSaldo().compareTo(saldoInformado) >= 0);
		cartaoValido.orElseThrow(() -> new SaldoInsuficienteException());
	}

	private void validarSenha(Cartao cartao, String senhaInformada) {
		Optional<Cartao> cartaoValido = Optional.of(cartao)
				.filter(cartaoBanco -> cartaoBanco.getSenha().equals(senhaInformada));
		cartaoValido.orElseThrow(() -> new SenhaInvalidaException());
	}
}
