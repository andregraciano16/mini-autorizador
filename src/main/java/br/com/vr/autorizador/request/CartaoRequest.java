package br.com.vr.autorizador.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class CartaoRequest {

	private String numeroCartao;
	private String senha;

}
