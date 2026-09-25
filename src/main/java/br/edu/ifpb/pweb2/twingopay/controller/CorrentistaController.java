package br.edu.ifpb.pweb2.twingopay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.twingopay.model.Correntista;
import br.edu.ifpb.pweb2.twingopay.repository.CorrentistaRepository;

@Controller
@RequestMapping("/correntistas")
public class CorrentistaController {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @GetMapping("/form")
    public String getForm(Correntista correntista, Model model) {
        model.addAttribute("correntista", correntista);
        return "correntistas/form";
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("correntistas", correntistaRepository.findAll());
        return "correntistas/list";
    }

    @PostMapping("/save")
    public String save(Correntista correntista, RedirectAttributes attr, Model model) {
        if (correntista.getNome().length() == 0) {
            model.addAttribute("mensagem", "É necessário informar um nome.");
            return "correntistas/form";
        }
        if (correntista.getNome().length() > 50) {
            model.addAttribute("mensagem", "Tamanho máximo do campo nome é 50 caracteres.");
            return "correntistas/form";
        }
        if (correntista.getSenha().length() == 0) {
            model.addAttribute("mensagem", "Senha obrigatória.");
            return "correntistas/form";
        }
        if (correntista.getEmail().length() == 0) {
            model.addAttribute("mensagem", "Informe um e-mail válido.");
            return "correntistas/form";
        }

        correntistaRepository.save(correntista);
        model.addAttribute("correntistas", correntistaRepository.findAll());
        model.addAttribute("mensagem", "Cadastro efetuado de " + correntista.getNome() + " efetuado.");
        return "redirect:/correntistas/list";

    }
}
