package br.com.vr.autorizador.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.vr.autorizador.entity.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, String> {

	@Query("select c.saldo from Cartao c where c.numeroCartao = :numeroCartao")
	public BigDecimal findSaldoByNumeroCartao(String numeroCartao);

}
