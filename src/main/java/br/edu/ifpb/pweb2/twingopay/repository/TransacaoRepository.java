package br.edu.ifpb.pweb2.twingopay.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.twingopay.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
    List<Transacao> findByConta_Correntista_IdAndDataBetween(Integer correntistaId, LocalDate inicio, LocalDate fim);
}
