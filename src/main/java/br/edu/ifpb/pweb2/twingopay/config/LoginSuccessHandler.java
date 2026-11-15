package br.edu.ifpb.pweb2.twingopay.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.twingopay.model.Correntista;
import br.edu.ifpb.pweb2.twingopay.repository.CorrentistaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final CorrentistaRepository repo;

    public LoginSuccessHandler(CorrentistaRepository repo) {
        this.repo = repo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication auth) throws IOException {
        Correntista c = repo.findByUsername(auth.getName()).orElseThrow();
        request.getSession().setAttribute("correntistaLogado", c);
        String destino = c.isAdmin() ? "/admin/painelAdmin" : "/correntistas/list";
        response.sendRedirect(request.getContextPath() + destino);
    }

}
