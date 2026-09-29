package br.edu.ifpb.pweb2.twingopay.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ifpb.pweb2.twingopay.model.Categoria;
import br.edu.ifpb.pweb2.twingopay.model.Correntista;
import br.edu.ifpb.pweb2.twingopay.repository.CategoriaRepository;
import br.edu.ifpb.pweb2.twingopay.repository.TransacaoRepository;
import jakarta.servlet.http.HttpSession;

@Controller 
@RequestMapping("/orcamento")
public class OrcamentoController {

    @Autowired 
    private CategoriaRepository categoriaRepository;

    @Autowired 
    TransacaoRepository transacaoRepository;

    @GetMapping
    public String verOrcamento(
        @RequestParam(value = "ano", required = false) Integer ano,
        HttpSession session,
        Model model) {
            
            Correntista correntista = (Correntista) session.getAttribute("correntistaLogado");
            if (correntista == null) {
                return "redirect:/login";
            }
            
            if (ano == null) {
                ano = LocalDate.now().getYear();
            }

            List<Categoria> categorias = categoriaRepository.findAll();
            categorias.sort((c1, c2) -> Integer.compare(c1.getOrdem(), c2.getOrdem()));

            model.addAttribute("categorias", categorias);
            model.addAttribute("anoSelecionado", ano);

            return "orcamento/anual";
        }

}
