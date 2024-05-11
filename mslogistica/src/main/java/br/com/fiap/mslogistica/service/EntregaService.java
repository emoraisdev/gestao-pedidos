package br.com.fiap.mslogistica.service;

import br.com.fiap.mslogistica.model.Entrega;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntregaService {

    Entrega incluir(Entrega entregador);

    Entrega buscar(Long id);

    Entrega alterar(Entrega entregador);

    boolean remover(Long id);

    Page<Entrega> listar(Pageable pageable);
}
