package br.edu.ifpb.pweb2.twingopay.model;

import java.math.BigDecimal;
import java.util.List;

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
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_conta")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"transacoes", "correntista"})
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String numero;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private TipoConta tipo;
    private Integer diaFechamento; // usamos quando é tipo cartão

    @ManyToOne
    private Correntista correntista;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes;

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
