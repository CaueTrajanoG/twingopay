package br.edu.ifpb.pweb2.twingopay.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.twingopay.model.Correntista;
import br.edu.ifpb.pweb2.twingopay.repository.CorrentistaRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String logar(@RequestParam("username") String username,
            @RequestParam("password") String senha,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Optional<Correntista> correntistaOpt = correntistaRepository.findByNome(username);

        if (correntistaOpt.isEmpty() || !correntistaOpt.get().getSenha().equals(senha)) {
            return "redirect:/login?error";
        }

        Correntista correntista = correntistaOpt.get();

        if (!correntista.isAdmin()) {
            return "redirect:/correntistas/list";
        }

        session.setAttribute("correntistaLogado", correntista);
        return "redirect:/admin/painelAdmin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
