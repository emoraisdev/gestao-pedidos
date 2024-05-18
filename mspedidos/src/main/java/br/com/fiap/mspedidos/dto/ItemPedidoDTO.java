package br.com.fiap.mspedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO {

    @NotNull(message = "Produto não informado")
    private Long produtoId;

    @NotNull(message = "Quantidade do produto não informado")
    private Integer quantidade;

}
