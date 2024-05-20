package br.com.fiap.mslogistica.util;

import br.com.fiap.mslogistica.model.Endereco;
import br.com.fiap.mslogistica.model.Entregador;

public class EntregadorHelper {

    public static Entregador gerarEntregador() {
        return Entregador.builder()
                .nome("Pedro Jonas")
                .idade(32)
                .endereco(Endereco.builder().rua("XV de Novembro")
                        .bairro("Centro")
                        .numero("971")
                        .cep("80060-000")
                        .cidade("Curitiba")
                        .estado("PR")
                        .pais("Brasil").build()).build();
    }
}
