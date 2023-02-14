package br.com.vr.autorizador.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
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

import br.com.vr.autorizador.enums.MensagemErroEnum;
import br.com.vr.autorizador.exception.CartaoInexistenteException;
import br.com.vr.autorizador.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.exception.SenhaInvalidaException;
import br.com.vr.autorizador.request.TransacaoRequest;
import br.com.vr.autorizador.service.TransacaoService;

@AutoConfigureJsonTesters
@WebMvcTest(TransacaoController.class)
public class TransacaoControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<TransacaoRequest> jsonTransacaoRest;

	@MockBean
	private TransacaoService transacaoService;

	private static TransacaoRequest transacaoRequest;
	
	@BeforeAll
	public static void carregarDados() {
		transacaoRequest = TransacaoRequest.builder().numeroCartao("12345678912345672")
				.senhaCartao("123456").valor(new BigDecimal(10)).build();
	}
	
	@Test
	public void deveRealizarUmaTransacaoComSucesso() throws IOException, Exception {
		MockHttpServletResponse response = mvc.perform(post("/transacoes")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonTransacaoRest.write(transacaoRequest).getJson()))
			.andReturn()
			.getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo("OK");
	}
	
	@Test
	public void deveValidarQueOSaldoEInsuficiente() throws IOException, Exception {
		given(transacaoService.realizarTransacao(transacaoRequest)).willThrow(new SaldoInsuficienteException());
		MockHttpServletResponse response = mvc.perform(post("/transacoes")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonTransacaoRest.write(transacaoRequest).getJson()))
			.andReturn()
			.getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(response.getContentAsString()).isEqualTo(MensagemErroEnum.SALDO_INSUFICIENTE.getMensagem());
	}
	
	@Test
	public void deveValidarSenhaInvalida() throws IOException, Exception {
		given(transacaoService.realizarTransacao(transacaoRequest)).willThrow(new SenhaInvalidaException());
		MockHttpServletResponse response = mvc.perform(post("/transacoes")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonTransacaoRest.write(transacaoRequest).getJson()))
			.andReturn()
			.getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(response.getContentAsString()).isEqualTo(MensagemErroEnum.SENHA_INVALIDA.getMensagem());
	}
	
	@Test
	public void deveValidarQueOCartaoEInexistente() throws IOException, Exception {
		given(transacaoService.realizarTransacao(transacaoRequest)).willThrow(new CartaoInexistenteException());
		MockHttpServletResponse response = mvc.perform(post("/transacoes")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonTransacaoRest.write(transacaoRequest).getJson()))
			.andReturn()
			.getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(response.getContentAsString()).isEqualTo(MensagemErroEnum.CARTAO_INEXISTENTE.getMensagem());
	}

}
