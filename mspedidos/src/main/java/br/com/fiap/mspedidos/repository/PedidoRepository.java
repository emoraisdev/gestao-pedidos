package br.com.fiap.mspedidos.repository;

import br.com.fiap.mspedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, String> {
}
