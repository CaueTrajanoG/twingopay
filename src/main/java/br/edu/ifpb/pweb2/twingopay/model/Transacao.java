package br.edu.ifpb.pweb2.twingopay.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.web.bind.annotation.Mapping;

import br.edu.ifpb.pweb2.twingopay.enuns.Movimento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Transacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String descricao;
    private BigDecimal valor;
    private Movimento movimento;
    private LocalDate data;

    @ManyToOne
    private Conta conta;

    @ManyToOne
    private Categoria categoria;
}
