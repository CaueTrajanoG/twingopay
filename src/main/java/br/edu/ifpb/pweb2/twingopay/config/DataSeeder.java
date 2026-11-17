package br.edu.ifpb.pweb2.twingopay.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.twingopay.enuns.Natureza;
import br.edu.ifpb.pweb2.twingopay.model.Categoria;
import br.edu.ifpb.pweb2.twingopay.model.Correntista;
import br.edu.ifpb.pweb2.twingopay.repository.CategoriaRepository;
import br.edu.ifpb.pweb2.twingopay.repository.CorrentistaRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CorrentistaRepository correntistaRepo;
    private final CategoriaRepository categoriaRepo;
    private final PasswordEncoder encoder;

    public DataSeeder(CorrentistaRepository correntistaRepo,
            CategoriaRepository categoriaRepo,
            PasswordEncoder encoder) {
        this.correntistaRepo = correntistaRepo;
        this.categoriaRepo = categoriaRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        criarAdmin();
        criarCategorias();
    }

    private void criarAdmin() {
        if (correntistaRepo.findByUsername("admin").isEmpty()) {
            Correntista c = new Correntista();
            c.setUsername("admin");
            c.setSenha(encoder.encode("admin123"));
            c.setAdmin(true);
            correntistaRepo.save(c);
        }
    }

    private void criarCategorias() {
        if (categoriaRepo.count() > 0) {
            return;
        }
        criar("Salário", Natureza.ENTRADA, 1);
        criar("Alimentação", Natureza.SAIDA, 2);
        criar("Moradia", Natureza.SAIDA, 3);
        criar("Transporte", Natureza.SAIDA, 4);
        criar("Educação", Natureza.SAIDA, 5);
        criar("Lazer", Natureza.SAIDA, 6);
        criar("Saúde", Natureza.SAIDA, 7);
        criar("Investimentos", Natureza.INVESTIMENTO, 8);
    }

    private void criar(String nome, Natureza natureza, int ordem) {
        Categoria cat = new Categoria();
        cat.setNome(nome);
        cat.setNatureza(natureza);
        cat.setOrdem(ordem);
        cat.setAtivo(true);
        categoriaRepo.save(cat);
    }
}