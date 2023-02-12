package br.com.vr.autorizador.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {

	@Id
	@Column(name = "numero_cartao", length = 16, nullable = false)
	private String numeroCartao;

	@Column(name = "senha", length = 6, nullable = false)
	private String senha;

	@Column(name = "saldo", nullable = false)
	private BigDecimal saldo;

}
