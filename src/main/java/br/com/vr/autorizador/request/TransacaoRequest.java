package br.com.vr.autorizador.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class TransacaoRequest {

	@NotEmpty(message = "{mensagem.validacao.numero.cartao.obrigatorio}")
	@Size(max = 16, message = "{mensagem.validacao.numero.cartao.tamanho}")
	private String numeroCartao;
	
	@NotEmpty(message = "{mensagem.validacao.senha.cartao.obrigatorio}")
	@Size(min = 6, max = 6, message = "{mensagem.validacao.senha.cartao.tamanho}")
	private String senhaCartao;
	
	@NotNull(message = "{mensagem.validacao.valor.nao.nulo}")
	private BigDecimal valor;
	
}
