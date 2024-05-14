package br.com.fiap.mslogistica.service;

import br.com.fiap.mslogistica.model.Entregador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntregadorService {

    Entregador incluir(Entregador entregador);

    Entregador buscar(Long id);

    Entregador alterar(Entregador entregador);

    boolean remover(Long id);

    Page<Entregador> listar(Pageable pageable);
}
