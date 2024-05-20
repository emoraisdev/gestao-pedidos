package br.com.fiap.mspedidos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal valorUnitario;
}
