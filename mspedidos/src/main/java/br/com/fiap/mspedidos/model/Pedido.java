package br.com.fiap.mspedidos.model;

import lombok.Data;

import java.util.Calendar;
import java.util.List;

@Data
public class Pedido {
    private Long id;
    private Long clientId;
    private List<ItemPedido> itensPedido;
    private Calendar dataPedido;
    private StatusPedido status;
}
