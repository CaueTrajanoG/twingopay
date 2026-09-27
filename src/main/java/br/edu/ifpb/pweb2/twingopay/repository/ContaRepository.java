package br.edu.ifpb.pweb2.twingopay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.twingopay.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {
    List<Conta> findByCorrentista_id(Integer correntista_id);
}
