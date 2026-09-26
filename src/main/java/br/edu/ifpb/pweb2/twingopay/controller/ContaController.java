package br.edu.ifpb.pweb2.twingopay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.twingopay.enuns.TipoConta;
import br.edu.ifpb.pweb2.twingopay.model.Conta;
import br.edu.ifpb.pweb2.twingopay.repository.ContaRepository;
import br.edu.ifpb.pweb2.twingopay.repository.CorrentistaRepository;

@Controller
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired 
    private CorrentistaRepository correntistaRepository;

    @GetMapping("/form")
    public String getForm(Conta conta, Model model){
        model.addAttribute("conta", conta);
        model.addAttribute("correntistas", correntistaRepository.findAll());
        model.addAttribute("tipos", TipoConta.values());
        return "contas/form";
    }

    @GetMapping ("/list")
    public String getList(Model model){
        model.addAttribute("contas", contaRepository.findAll());
        return "contas/list";
    }

    @PostMapping ("/save")
    public String save(Conta conta, RedirectAttributes attr, Model model){
        attr.addFlashAttribute("mensagem", "Conta criada com sucesso");
        contaRepository.save(conta);
        return "redirect:/contas/list";
    }
}
