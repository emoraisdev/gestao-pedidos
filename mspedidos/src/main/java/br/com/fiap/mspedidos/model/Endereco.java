package br.com.fiap.mspedidos.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Endereco {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
}
