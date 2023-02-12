package br.com.vr.autorizador.request;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class TransacaoRequest {

	private String numeroCartao;
	private String senhaCartao;
	private BigDecimal valor;
	
}
