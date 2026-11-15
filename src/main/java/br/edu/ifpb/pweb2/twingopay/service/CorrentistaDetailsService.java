package br.edu.ifpb.pweb2.twingopay.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.twingopay.model.Correntista;
import br.edu.ifpb.pweb2.twingopay.repository.CorrentistaRepository;

@Service
public class CorrentistaDetailsService implements UserDetailsService {

    private final CorrentistaRepository repo;

    public CorrentistaDetailsService(CorrentistaRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Correntista c = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return User.withUsername(c.getUsername())
                .password(c.getSenha())
                .roles(c.isAdmin() ? "ADMIN" : "USER")
                .build();
    }
}