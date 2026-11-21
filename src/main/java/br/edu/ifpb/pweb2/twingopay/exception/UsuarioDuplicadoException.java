package br.edu.ifpb.pweb2.twingopay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsuarioDuplicadoException extends RuntimeException {
    public UsuarioDuplicadoException(String mensagem) {
        super(mensagem);
    }
}
