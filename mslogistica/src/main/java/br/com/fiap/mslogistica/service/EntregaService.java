package br.com.fiap.mslogistica.service;

import br.com.fiap.mslogistica.model.Coordenada;
import br.com.fiap.mslogistica.model.Entrega;
import br.com.fiap.mslogistica.model.dto.EntregaStatusDTO;
import br.com.fiap.mslogistica.model.enums.EntregaStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntregaService {

    Entrega incluir(Entrega entregador);

    Entrega buscar(Long id);

    Entrega alterar(Entrega entregador);

    boolean definirLocalEntregador(Long entregaID, Coordenada coordenada);

    boolean remover(Long id);

    Page<Entrega> listar(Pageable pageable);

    Entrega atualizarStatus(Long entregaID, EntregaStatusDTO status);
}
