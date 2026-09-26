package br.edu.ifpb.pweb2.twingopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.twingopay.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

}
