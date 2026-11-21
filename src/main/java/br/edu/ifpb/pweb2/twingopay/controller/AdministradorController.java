package br.edu.ifpb.pweb2.twingopay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.twingopay.exception.DadosInvalidosException;
import br.edu.ifpb.pweb2.twingopay.exception.UsuarioDuplicadoException;
import br.edu.ifpb.pweb2.twingopay.model.Correntista;
import br.edu.ifpb.pweb2.twingopay.repository.CorrentistaRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdministradorController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CorrentistaRepository correntistaRepository;

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("correntistas", correntistaRepository.findAll());
        return "correntistas/list";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "admin/loginAdmin";
    }

    @PostMapping("/criarCorrentista")
    public String CriarCorrentista(Correntista correntista, RedirectAttributes attr, Model model) {
        correntista.setSenha(passwordEncoder.encode(correntista.getSenha()));

        if (correntista.getUsername() == null || correntista.getUsername().isBlank()) {
            throw new DadosInvalidosException("Informe o usuário.");
        }
        if (correntista.getSenha() == null || correntista.getSenha().length() < 6) {
            throw new DadosInvalidosException("A senha deve ter pelo menos 6 caracteres.");
        }
        if (correntistaRepository.existsByUsername(correntista.getUsername())) {
            throw new UsuarioDuplicadoException("O usuário '" + correntista.getUsername() + "' já está em uso.");
        }

        correntistaRepository.save(correntista);
        attr.addFlashAttribute("mensagem", "Cadastro efetuado de " + correntista.getNome() + " efetuado.");
        model.addAttribute("correntistas", correntistaRepository.findAll());
        return "redirect:/admin/painelAdmin";
    }

    @GetMapping("/painelAdmin")
    public String painelAdmin(HttpSession session, Model model) {
        Correntista admin = (Correntista) session.getAttribute("correntistaLogado");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }

        model.addAttribute("admin", admin);
        model.addAttribute("correntistas", correntistaRepository.findAll());
        return "admin/painelAdmin";
    }

    @GetMapping("/correntistas/novo")
    public String formNovoCorrentista(HttpSession session, Model model) {
        Correntista admin = (Correntista) session.getAttribute("correntistaLogado");
        if (admin == null || !admin.isAdmin()) {
            return "redirect:/login";
        }
        model.addAttribute("correntista", new Correntista());
        return "correntistas/form";
    }

}
