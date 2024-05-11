package br.com.fiap.mspedidos.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemPedido {
    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal preco;

}
