package br.com.vr.autorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vr.autorizador.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, String>{

}
