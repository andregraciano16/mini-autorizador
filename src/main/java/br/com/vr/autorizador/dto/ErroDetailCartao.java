package br.com.vr.autorizador.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErroDetailCartao {

	private String numeroCartao;
	private String senha;
	
}
