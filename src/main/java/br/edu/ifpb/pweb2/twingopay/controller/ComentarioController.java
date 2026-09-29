package br.edu.ifpb.pweb2.twingopay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.twingopay.model.Comentario;
import br.edu.ifpb.pweb2.twingopay.model.Transacao;
import br.edu.ifpb.pweb2.twingopay.repository.ComentarioRepository;
import br.edu.ifpb.pweb2.twingopay.repository.TransacaoRepository;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @GetMapping("/transacao/{transacaoId}")
    public String formComentario(
        @PathVariable("transacaoId") Integer transacaoId,
        Model model, 
        HttpSession session) {
        if (session.getAttribute("correntistaLogado") == null) {
            return "redirect:/login";
        }

        Optional<Transacao> transacaoOpt = transacaoRepository.findById(transacaoId);
        if (transacaoOpt.isEmpty()) {
            return "redirect:/correntistas/list";
        }

        Transacao transacao = transacaoOpt.get();
    
        Comentario comentario = comentarioRepository.findAll().stream()
                .filter(com -> com.getTransacao() != null && com.getTransacao().getId().equals(transacaoId))
                .findFirst()
                .orElse(new Comentario());

        if (comentario.getTransacao() == null) {
            comentario.setTransacao(transacao);
        }

        model.addAttribute("comentario", comentario);
        model.addAttribute("transacao", transacao);

        return "transacoes/comentarioForm";
    }

    @PostMapping("/salvar")
    public String salvarComentario(Comentario comentario, RedirectAttributes redirectAttributes) {
        if (comentario.getTransacao() != null && comentario.getTransacao().getId() != null) {
            Transacao transacao = transacaoRepository.findById(comentario.getTransacao().getId()).orElse(null);
            if (transacao != null) {
                comentario.setTransacao(transacao);
                comentarioRepository.save(comentario);
                redirectAttributes.addFlashAttribute("mensagem", "Comentário salvo com sucesso!");
                
                return "redirect:/correntistas/contas/" + transacao.getConta().getId();
            }
        }
        
        redirectAttributes.addFlashAttribute("erro", "Erro ao salvar o comentário.");
        return "redirect:/correntistas/list";
    }

    @GetMapping("/excluir/{id}")
    public String excluirComentario(
        @PathVariable Integer id,
        RedirectAttributes redirectAttributes,
        HttpSession session) {
            if(session.getAttribute("correntistaLogado") == null) {
                return "redirect:/login";
            }

            Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
            if(comentarioOpt.isPresent()) {
                Comentario comentario = comentarioOpt.get();
                Integer contaId = comentario.getTransacao().getConta().getId();

                comentarioRepository.delete(comentario);
                redirectAttributes.addFlashAttribute("mensagem", "Comentário excluído!");

                return "redirect:/correntistas/contas/" + contaId;
            }

            return "redirect:/correntistas/list";
        }
}