package br.com.fiap.mspedidos.repository;

import br.com.fiap.mspedidos.model.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PedidoRepository extends MongoRepository<Pedido, String> {
}
