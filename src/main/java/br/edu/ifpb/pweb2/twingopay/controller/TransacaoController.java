package br.edu.ifpb.pweb2.twingopay.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.twingopay.enuns.Movimento;
import br.edu.ifpb.pweb2.twingopay.model.Conta;
import br.edu.ifpb.pweb2.twingopay.model.Transacao;
import br.edu.ifpb.pweb2.twingopay.repository.CategoriaRepository;
import br.edu.ifpb.pweb2.twingopay.repository.ContaRepository;
import br.edu.ifpb.pweb2.twingopay.repository.TransacaoRepository;
import jakarta.servlet.http.HttpSession;

@Controller 
@RequestMapping ("/transacoes")
public class TransacaoController {

    @Autowired 
    private TransacaoRepository transacaoRepository;

    @Autowired 
    private ContaRepository contaRepository;

    @Autowired 
    private CategoriaRepository categoriaRepository;

    @GetMapping("/nova")
    public String formNovaTransacao(
        @RequestParam Integer contaId,
        Model model,
        HttpSession session) {
            if (session.getAttribute("correntistaLogado") == null) {
                return "redirect:/login";
            }

            Optional<Conta> contaOpt = contaRepository.findById(contaId);
            if (contaOpt.isEmpty()) {
                return "redirect:/correntistas/list";
            }

            Transacao transacao = new Transacao();
            transacao.setConta(contaOpt.get());

            model.addAttribute("transacao", transacao);
            model.addAttribute("conta", contaOpt.get());
            model.addAttribute("categorias", categoriaRepository.findAll());
            model.addAttribute("movimentos", Movimento.values());

            return "transacoes/form";
        } 

        @PostMapping("/salvar")
        public String salvarTransacao(Transacao transacao, RedirectAttributes redirectAttributes) {
            transacaoRepository.save(transacao);
            redirectAttributes.addFlashAttribute("mensagem", "Transação salva!");
            return "redirect:/correntistas/contas/" + transacao.getConta().getId();
        }

}
