package br.edu.ifpb.pweb2.twingopay.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.twingopay.model.Correntista;
import br.edu.ifpb.pweb2.twingopay.repository.CorrentistaRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CorrentistaRepository repo;
    private final PasswordEncoder encoder;

    public DataSeeder(CorrentistaRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (repo.findByUsername("admin").isEmpty()) {
            Correntista c = new Correntista();
            c.setUsername("admin");
            c.setSenha(encoder.encode("admin123"));
            c.setAdmin(true);
            repo.save(c);
        }
    }
}