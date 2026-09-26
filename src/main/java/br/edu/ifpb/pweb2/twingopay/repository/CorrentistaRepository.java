package br.edu.ifpb.pweb2.twingopay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.twingopay.model.Correntista;

@Repository
public interface CorrentistaRepository extends JpaRepository<Correntista, Integer> {
    Optional<Correntista> findByNome(String nome);
}
