package br.edu.ifpb.pweb2.twingopay.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ifpb.pweb2.twingopay.enuns.Movimento;
import br.edu.ifpb.pweb2.twingopay.model.Categoria;
import br.edu.ifpb.pweb2.twingopay.model.Correntista;
import br.edu.ifpb.pweb2.twingopay.model.Transacao;
import br.edu.ifpb.pweb2.twingopay.repository.CategoriaRepository;
import br.edu.ifpb.pweb2.twingopay.repository.TransacaoRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/orcamento")
public class OrcamentoController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

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

        List<Transacao> transacoes = transacaoRepository.findByConta_Correntista_IdAndDataBetween(
                correntista.getId(), LocalDate.of(ano, 1, 1), LocalDate.of(ano, 12, 31));

        // 12 meses
        Map<Integer, BigDecimal[]> valores = new HashMap<>();
        for (Categoria c : categorias) {
            BigDecimal[] linha = new BigDecimal[13];
            Arrays.fill(linha, BigDecimal.ZERO);
            valores.put(c.getId(), linha);
        }

        for (Transacao t : transacoes) {
            if (t.getCategoria() == null || t.getData() == null || t.getValor() == null) {
                continue;
            }
            BigDecimal[] linha = valores.get(t.getCategoria().getId());
            if (linha == null) {
                continue;
            }
            BigDecimal v = t.getMovimento() == Movimento.DEBITO ? t.getValor().negate() : t.getValor();
            int mes = t.getData().getMonthValue() - 1;
            linha[mes] = linha[mes].add(v);
            linha[12] = linha[12].add(v);
        }

        model.addAttribute("categorias", categorias);
        model.addAttribute("valores", valores);
        model.addAttribute("anoSelecionado", ano);
        model.addAttribute("titulo", "Orçamento Anual");

        return "orcamento/anual";
    }
}