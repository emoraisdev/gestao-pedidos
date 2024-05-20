package br.com.fiap.mslogistica.util;

import br.com.fiap.mslogistica.model.Endereco;
import br.com.fiap.mslogistica.model.Entrega;
import br.com.fiap.mslogistica.model.Entregador;
import br.com.fiap.mslogistica.model.enums.EntregaStatus;

public class EntregaHelper {

    public static Entrega gerarEntrega() {
        return Entrega.builder()
                .pedidoId(1L)
                .entregador(Entregador.builder().id(101L).build())
                .status(EntregaStatus.EM_ANDAMENTO)
                .origem(Endereco.builder().id(101L).build())
                .destino(Endereco.builder().id(102L).build()).build();
    }
}
