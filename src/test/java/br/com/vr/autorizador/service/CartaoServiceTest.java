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
import br.com.vr.autorizador.exception.CartaoJaExisteException;
import br.com.vr.autorizador.exception.CartaoNaoEncontradoException;
import br.com.vr.autorizador.repository.CartaoRepository;
import br.com.vr.autorizador.request.CartaoRequest;

@SpringBootTest
public class CartaoServiceTest {

	private static final String NUMERO_CARTAO = "12345678912345672";

	@Autowired
	private CartaoService cartaoService;

	@MockBean
	private CartaoRepository cartaoRepository;

	private static Optional<Cartao> cartao;
	private static CartaoRequest cartaoRequest;
	
	@BeforeAll
	public static void carregarDados() {
		cartao = Optional
				.of(Cartao.builder().numeroCartao(NUMERO_CARTAO).senha("123456").saldo(new BigDecimal(10)).build());
		cartaoRequest = CartaoRequest.builder().numeroCartao(NUMERO_CARTAO).senha("123456").build();
	}

	@Test
	public void deveObterOSaldo() {
		when(cartaoRepository.findById(NUMERO_CARTAO)).thenReturn(cartao);
		BigDecimal saldo = cartaoService.obterSaldo(NUMERO_CARTAO);
		assertEquals(saldo, cartao.get().getSaldo());
	}
	
	@Test
	public void deveInformaQueOCartaoNaoExisteOsTentarPegarSaldo() {
        assertThrows(CartaoNaoEncontradoException.class, () -> cartaoService.obterSaldo(NUMERO_CARTAO));
	}
	
	@Test
	public void deveAtualizarOSaldo() {
        BigDecimal saldoADebitar = new BigDecimal(10);
        BigDecimal saldoDebitado = cartao.get().getSaldo().subtract(saldoADebitar);
		when(cartaoRepository.saveAndFlush(cartao.get())).thenReturn(cartao.get());
		Cartao cartaoAtualizado = cartaoService.atualizarSaldo(cartao.get(), new BigDecimal(10));
		assertEquals(saldoDebitado, cartaoAtualizado.getSaldo());
	}
	
	@Test
	public void deveLancarExcecaoNoCadastroJaQueOCartaoExiste() {
		when(cartaoRepository.findById(NUMERO_CARTAO)).thenReturn(cartao);
        assertThrows(CartaoJaExisteException.class, () -> cartaoService.cadastrar(cartaoRequest));
	}
	
	@Test
	public void deveLancarCadastrarOcartaoComSaldoDe500() {
		BigDecimal saldoDe500 = new BigDecimal(500);
		Cartao cartao = Cartao.builder().numeroCartao(NUMERO_CARTAO).senha("123456").saldo(saldoDe500).build();
		when(cartaoRepository.findById(NUMERO_CARTAO)).thenReturn(Optional.empty());
		when(cartaoRepository.saveAndFlush(cartao)).thenReturn(cartao);
		assertEquals(saldoDe500, cartaoService.cadastrar(cartaoRequest).getSaldo());
	}

}
