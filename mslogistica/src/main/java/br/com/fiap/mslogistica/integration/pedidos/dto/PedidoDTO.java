package br.com.fiap.mslogistica.integration.pedidos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PedidoDTO(
        String id,
        Long clientId,
        EnderecoDTO enderecoEntrega
) {
}
