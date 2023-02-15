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

	@NotEmpty(message = "Número do cartão é obrigatório")
	@Size(max = 16, message = "Numero do cartão deve conter no máximo 16 digitos")
	private String numeroCartao;
	
	@NotEmpty(message = "Senha é obrigatória")
	@Size(min = 6, max = 6, message = "Senha deve conter 6 números")
	private String senhaCartao;
	
	@NotNull(message = "Valor não pode ser nulo")
	private BigDecimal valor;
	
}
