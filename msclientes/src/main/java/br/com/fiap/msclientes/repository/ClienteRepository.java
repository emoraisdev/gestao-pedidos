package br.com.fiap.msclientes.repository;

import br.com.fiap.msclientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
