package br.com.vr.autorizador.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import br.com.vr.autorizador.exception.CartaoJaExisteException;
import br.com.vr.autorizador.exception.CartaoNaoEncontradoException;
import br.com.vr.autorizador.repository.CartaoRepository;
import br.com.vr.autorizador.request.CartaoRequest;
import br.com.vr.autorizador.service.CartaoService;

@AutoConfigureJsonTesters
@WebMvcTest(CartaoController.class)
public class CartaoControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<CartaoRequest> jsonCartaoRequest;

	@Autowired
	private JacksonTester<BigDecimal> jsonSaldo;
	
	@MockBean
	private CartaoRepository cartaoRepository;
	
	@MockBean
	private CartaoService cartaoService;
	
	@Test
	public void deveCadastrarUmCartao() throws Exception {
		CartaoRequest cartao = CartaoRequest.builder().numeroCartao("12345678912345672").senha("123456").build();
		
		given(cartaoService.cadastrar(cartao)).willReturn(cartao);
	
		MockHttpServletResponse response = mvc.perform(post("/cartoes")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonCartaoRequest.write(cartao).getJson()))
			.andReturn()
			.getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonCartaoRequest.write(cartao).getJson());
	}
	
	@Test
	public void deveVerificarQueOCartaoJaExiste() throws Exception {
		CartaoRequest cartao = CartaoRequest.builder().numeroCartao("12345678912345672").senha("123456").build();
		
		given(cartaoService.cadastrar(cartao)).willThrow(new CartaoJaExisteException(cartao.getNumeroCartao(), cartao.getSenha()));

		MockHttpServletResponse responseError = mvc.perform(post("/cartoes")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonCartaoRequest.write(cartao).getJson()))
			.andReturn()
			.getResponse();
     
		assertThat(responseError.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(responseError.getContentAsString()).isEqualTo(jsonCartaoRequest.write(cartao).getJson());

	}
	
	@Test
	public void deveObterOSaldoCartaoCadastrado () throws Exception {
		BigDecimal saldoCartao = new BigDecimal(500);
		String numeroCartao = "12345678912345672";
		
		given(cartaoService.obterSaldo(numeroCartao)).willReturn(saldoCartao);
	
		MockHttpServletResponse response = mvc.perform(get("/cartoes/" + numeroCartao)
				.accept(MediaType.APPLICATION_JSON))
			.andReturn()
			.getResponse();
	
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonSaldo.write(saldoCartao).getJson());
	}
	
	
	@Test
	public void deveRetornaErro404AoConsultarSaldo() throws Exception {
		String numeroCartao = "12345678912345672";
		
		given(cartaoService.obterSaldo(numeroCartao)).willThrow(new CartaoNaoEncontradoException());
	
		MockHttpServletResponse response = mvc.perform(get("/cartoes/" + numeroCartao)
				.accept(MediaType.APPLICATION_JSON))
			.andReturn()
			.getResponse();
	
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		assertThat(response.getContentAsString()).isEqualTo("");
	}

}
