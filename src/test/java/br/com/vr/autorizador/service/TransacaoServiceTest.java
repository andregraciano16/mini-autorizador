package br.com.vr.autorizador.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.vr.autorizador.entity.Cartao;
import br.com.vr.autorizador.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.exception.SenhaInvalidaException;
import br.com.vr.autorizador.repository.TransacaoRepository;
import br.com.vr.autorizador.request.TransacaoRequest;

@SpringBootTest
public class TransacaoServiceTest {

	private static final String NUMERO_CARTAO = "12345678912345672";
	private static final String SENHA_CARTAO = "123456";
	private static final String SENHA_CARTAO_INVALIDA = "1234567";
	private static final BigDecimal SAQUE = new BigDecimal(100);
	private static final BigDecimal SALDO = new BigDecimal(100);

	@Autowired
	private TransacaoService transacaoService;

	@MockBean
	private CartaoService cartaoService;
	
	@MockBean
	private TransacaoRepository transacaoRepository;

	private static Optional<Cartao> cartao;
	private static TransacaoRequest transacaoRequest;

	@BeforeAll
	public static void carregarDados() {
		cartao = Optional
				.of(Cartao.builder().numeroCartao(NUMERO_CARTAO).senha(SENHA_CARTAO).saldo(SALDO).build());
		transacaoRequest = TransacaoRequest.builder().numeroCartao(NUMERO_CARTAO)
				.senhaCartao(SENHA_CARTAO).valor(SAQUE).build();
	}

	@Test
	public void deveValidarASenhaEAcionarAExcecao() {
		TransacaoRequest transacaoRequest = TransacaoRequest.builder().numeroCartao(NUMERO_CARTAO)
				.senhaCartao(SENHA_CARTAO_INVALIDA).valor(SAQUE).build();
		when(cartaoService.findByNumeroCartao(transacaoRequest.getNumeroCartao())).thenReturn(cartao.get());
        assertThrows(SenhaInvalidaException.class, () -> this.transacaoService.realizarTransacao(transacaoRequest));
	}

	@Test
	public void deveValidarSaldoEAcionarAExcecao() {
		BigDecimal saqueSuperiorAoSaldo = SALDO.add(SAQUE);
		TransacaoRequest transacaoRequest = TransacaoRequest.builder().numeroCartao(NUMERO_CARTAO)
				.senhaCartao(SENHA_CARTAO).valor(saqueSuperiorAoSaldo).build();
		when(cartaoService.findByNumeroCartao(transacaoRequest.getNumeroCartao())).thenReturn(cartao.get());
        assertThrows(SaldoInsuficienteException.class, () -> this.transacaoService.realizarTransacao(transacaoRequest));
	}
	
	@Test
	public void deveRealizarATransacao() {
		when(cartaoService.findByNumeroCartao(transacaoRequest.getNumeroCartao())).thenReturn(cartao.get());
		assertEquals(this.transacaoService.realizarTransacao(transacaoRequest), transacaoRequest);
	}
}
