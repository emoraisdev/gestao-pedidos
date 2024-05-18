package br.com.fiap.mspedidos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoEntregaDTO {

    @NotNull(message = "logradouro não informado")
    private String logradouro;

    @NotNull(message = "numero não informado")
    private String numero;

    private String complemento;

    @NotNull(message = "bairro não informado")
    private String bairro;

    @NotNull(message = "cidade não informado")
    private String cidade;

    @NotNull(message = "uf não informado")
    private String uf;

    @NotNull(message = "cep não informado")
    private String cep;
}
