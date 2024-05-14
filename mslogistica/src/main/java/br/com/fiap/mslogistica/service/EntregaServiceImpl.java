package br.com.fiap.mslogistica.service;

import br.com.fiap.mslogistica.exception.BusinessException;
import br.com.fiap.mslogistica.exception.EntityNotFoundException;
import br.com.fiap.mslogistica.integration.NominationAPI;
import br.com.fiap.mslogistica.model.Coordenada;
import br.com.fiap.mslogistica.model.Entrega;
import br.com.fiap.mslogistica.model.enums.EntregaStatus;
import br.com.fiap.mslogistica.repository.EntregaRepository;
import br.com.fiap.mslogistica.service.util.OSMUrlBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EntregaServiceImpl implements EntregaService {

    private final EnderecoService enderecoService;

    private final EntregadorService entregadorService;

    private final EntregaRepository repo;

    private final NominationAPI nomitationAPI;

    @Override
    public Entrega incluir(Entrega entrega) {

        entregadorService.buscar(entrega.getEntregador().getId());

        entrega.setStatus(EntregaStatus.PREPARANDO);

        return repo.save(entrega);
    }

    @Override
    public Entrega buscar(Long id) {

        var entrega = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Entrega.class.getSimpleName()));

        try {
            nomitationAPI.definirLocal(entrega.getOrigem());
            nomitationAPI.definirLocal(entrega.getDestino());

            entrega.setUrlRota(OSMUrlBuilder.build(entrega.getOrigem().getCoordenada(),
                    entrega.getDestino().getCoordenada(), entrega.getLocalizacaoEntregador()));

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Erro ao gerar URL OpenStreetMap para a Rota.");
        }

        return entrega;
    }

    @Override
    public Entrega alterar(Entrega entrega) {

         buscar(entrega.getId());

        entregadorService.buscar(entrega.getEntregador().getId());

        return repo.save(entrega);
    }

    @Override
    public boolean definirLocalEntregador(Long entregaID, Coordenada coordenada) {
        var entrega = buscar(entregaID);
        entrega.setLocalizacaoEntregador(coordenada);
        repo.save(entrega);

        return true;
    }

    @Override
    public boolean remover(Long id) {
        buscar(id);
        repo.deleteById(id);

        return true;
    }

    @Override
    public Page<Entrega> listar(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
