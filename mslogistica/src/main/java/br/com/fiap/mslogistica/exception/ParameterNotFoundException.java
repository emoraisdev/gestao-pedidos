package br.com.fiap.mslogistica.exception;

public class ParameterNotFoundException extends RuntimeException{

    public ParameterNotFoundException(String param) {
        super("O Parâmetro %s é obrigatório.".formatted(param));
    }
}
