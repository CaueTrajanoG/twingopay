package br.edu.ifpb.pweb2.twingopay.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_correntista")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "contas")
public class Correntista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false) // não permite duplicidade
    private String username;

    private String senha;
    private boolean admin;

    public Integer getId() {
        return this.id;
    }

    public String getNome() {
        return this.username;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    @OneToMany(mappedBy = "correntista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conta> contas;
}
