package br.edu.ifpb.pweb2.twingopay.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import br.edu.ifpb.pweb2.twingopay.enuns.Movimento;
import br.edu.ifpb.pweb2.twingopay.enuns.TipoConta;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = { "transacoes", "correntista" })
public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String numero;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoConta tipo; // CORRENTE ou CARTAO

    private Integer diaFechamento; // só usado quando tipo == CARTAO

    @ManyToOne
    private Correntista correntista;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transacao> transacoes = new HashSet<>();

    public Conta(Correntista correntista) {
        this.correntista = correntista;
    }

    public BigDecimal getSaldo() {
        BigDecimal total = BigDecimal.ZERO;
        for (Transacao t : this.transacoes) {

            if (t.getMovimento() == Movimento.DEBITO) {
                total = total.subtract(t.getValor());
            } else {
                total = total.add(t.getValor());
            }
        }
        return total;
    }

}