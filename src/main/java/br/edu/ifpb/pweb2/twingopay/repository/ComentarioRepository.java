package br.edu.ifpb.pweb2.twingopay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.twingopay.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    Optional<Comentario> findByTransacao_Id(Integer transacaoId);
}
