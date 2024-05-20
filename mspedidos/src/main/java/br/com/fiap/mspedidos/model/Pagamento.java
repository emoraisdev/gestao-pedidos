package br.com.fiap.mspedidos.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Pagamento {
    private FormaPagamento formaPagamento;
    private BigDecimal valorTotal;
}
