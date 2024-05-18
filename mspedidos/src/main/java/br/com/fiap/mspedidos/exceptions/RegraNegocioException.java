package br.com.fiap.mspedidos.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class RegraNegocioException extends RuntimeException {
    private final List<String> errors;

    public RegraNegocioException(List<String> errors) {
        this.errors = errors;
    }

}
