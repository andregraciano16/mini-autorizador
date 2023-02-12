package br.com.vr.autorizador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.autorizador.entity.Cartao;
import br.com.vr.autorizador.entity.Transacao;
import br.com.vr.autorizador.repository.TransacaoRepository;
import br.com.vr.autorizador.request.TransacaoRequest;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private CartaoService cartaoService;
	
	public TransacaoRequest realizarTransacao (TransacaoRequest transacaoRequest) {
		Cartao cartao = cartaoService.findByNumeroCartao(transacaoRequest.getNumeroCartao());
		Transacao transacao = Transacao.builder().valor(transacaoRequest.getValor()).cartao(cartao).build();
		transacaoRepository.save(transacao);
		cartaoService.atualizarSaldo(cartao, transacaoRequest.getValor());
		return transacaoRequest;
	}
}
