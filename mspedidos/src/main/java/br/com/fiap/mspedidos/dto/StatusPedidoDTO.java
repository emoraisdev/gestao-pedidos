package br.com.fiap.mspedidos.dto;

import br.com.fiap.mspedidos.model.StatusPedido;
import jakarta.validation.constraints.Pattern;

public record StatusPedidoDTO(
        StatusPedido status
) {
}
