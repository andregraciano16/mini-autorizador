package br.com.vr.autorizador.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transacao {

	@Id
	@GeneratedValue(generator = "id", strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Cartao cartao;

	@Column(name = "valor", nullable = false)
	private BigDecimal valor;

}
