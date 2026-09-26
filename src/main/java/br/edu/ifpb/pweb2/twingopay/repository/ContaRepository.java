package br.edu.ifpb.pweb2.twingopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.twingopay.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {

}
