package br.edu.ifpb.pweb2.twingopay.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.twingopay.model.Conta;
import br.edu.ifpb.pweb2.twingopay.model.Correntista;
import br.edu.ifpb.pweb2.twingopay.model.Transacao;
import br.edu.ifpb.pweb2.twingopay.repository.ContaRepository;
import br.edu.ifpb.pweb2.twingopay.repository.CorrentistaRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/correntistas")
public class CorrentistaController {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Autowired
    private ContaRepository contaRepository;

    @GetMapping("/form")
    public String getForm(Correntista correntista, Model model) {
        model.addAttribute("correntista", correntista);
        return "correntistas/form";
    }

    @GetMapping("/list")
    public String getList(Model model, HttpSession session) {
        Correntista correntista = (Correntista) session.getAttribute("correntistaLogado");
        if (correntista == null) {
            return "redirect:/login";
        }
        model.addAttribute("contas", contaRepository.findByCorrentista_id(correntista.getId()));
        return "correntistas/list";
    }

    @PostMapping("/save")
    public String save(Correntista correntista, RedirectAttributes attr, Model model) {
        correntistaRepository.save(correntista);
        model.addAttribute("correntistas", correntistaRepository.findAll());
        return "redirect:/correntistas/list";
    }

    @GetMapping("/contas/{id}")
    public String detalhesConta(
            @PathVariable Integer id,
            @RequestParam(value = "dataInicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
            @RequestParam(value = "dataFim", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim,
            HttpSession session, 
            Model model) {
        
        Correntista correntista = (Correntista) session.getAttribute("correntistaLogado");
        if (correntista == null) {
            return "redirect:/login";
        }

        Optional<Conta> contaOpt = contaRepository.findById(id);
        if (contaOpt.isEmpty() || !contaOpt.get().getCorrentista().getId().equals(correntista.getId())) {
            return "redirect:/correntistas/list";
        }

        Conta conta = contaOpt.get();
        List<Transacao> transacoesFiltradas = conta.getTransacoes();

        if (dataInicio != null && dataFim != null) {
            transacoesFiltradas = transacoesFiltradas.stream()
                .filter(t -> t.getData() != null && !t.getData().isBefore(dataInicio) && !t.getData().isAfter(dataFim))
                .collect(Collectors.toList());
        }

        model.addAttribute("conta", conta);
        model.addAttribute("transacoes", transacoesFiltradas);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);

        return "contas/detalhesConta";
    }

    @GetMapping("/contas/novaConta")
    public String formNovaConta(HttpSession session, Model model) {
        Correntista correntista = (Correntista) session.getAttribute("correntistaLogado");
        if (correntista == null) {
            return "redirect:/login";
        }
        model.addAttribute("conta", new Conta());
        return "contas/novaConta";
    }
}