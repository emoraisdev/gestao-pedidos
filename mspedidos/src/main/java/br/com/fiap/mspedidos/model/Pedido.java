package br.com.fiap.mspedidos.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Document("pedido")
public class Pedido {
    @Id
    private String id;
    private Long clientId;
    private List<ItemPedido> itensPedido;
    private Date dataPedido;
    private StatusPedido status;
    private Endereco enderecoEntrega;
    private Pagamento pagamento;
}
