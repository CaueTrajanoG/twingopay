package br.edu.ifpb.pweb2.twingopay.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.edu.ifpb.pweb2.twingopay.enuns.Movimento;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_transacao")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "conta")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate data;
    private String descricao;
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private Movimento movimento;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Conta conta;
}
