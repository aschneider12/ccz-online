package br.dev.as.ccz.api.exception;

public class CpfJaCadastradoException extends RuntimeException {
    public CpfJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}