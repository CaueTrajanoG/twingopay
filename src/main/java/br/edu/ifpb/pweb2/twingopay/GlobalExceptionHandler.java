package br.edu.ifpb.pweb2.twingopay;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.twingopay.exception.AcessoNegadoException;
import br.edu.ifpb.pweb2.twingopay.exception.RecursoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ModelAndView naoEncontrado(RecursoNaoEncontradoException ex, HttpServletRequest req) {
        return montar(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(AcessoNegadoException.class)
    public ModelAndView acessoNegado(AcessoNegadoException ex, HttpServletRequest req) {
        return montar(HttpStatus.FORBIDDEN, ex.getMessage(), req);
    }

    // qualquer falha não prevista
    @ExceptionHandler(Exception.class)
    public ModelAndView falhaInesperada(Exception ex, HttpServletRequest req) throws Exception {
        if (ex instanceof ErrorResponse
                || AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            throw ex;
        }
        log.error("Falha inesperada em {}", req.getRequestURI(), ex);
        return montar(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado. Tente novamente mais tarde.", req);
    }

    private ModelAndView montar(HttpStatus status, String mensagem, HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("error");
        mv.setStatus(status);
        mv.addObject("status", status.value());
        mv.addObject("message", mensagem);
        mv.addObject("path", req.getRequestURI());
        mv.addObject("timestamp", LocalDateTime.now());
        return mv;
    }

}
