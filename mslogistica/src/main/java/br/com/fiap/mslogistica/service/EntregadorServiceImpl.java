package br.com.fiap.mslogistica.service;

import br.com.fiap.mslogistica.exception.EntityNotFoundException;
import br.com.fiap.mslogistica.model.Entregador;
import br.com.fiap.mslogistica.repository.EntregadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EntregadorServiceImpl implements EntregadorService{

    private final EntregadorRepository repo;

    private final EnderecoService enderecoService;

    @Override
    public Entregador incluir(Entregador entregador) {

        var endereco = enderecoService.salvar(entregador.getEndereco());

        entregador.getEndereco().setId(endereco.getId());

        return repo.save(entregador);
    }

    @Override
    public Entregador buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Entregador.class.getSimpleName()));
    }

    @Override
    public Entregador alterar(Entregador entregador) {
        var entregadorDB = buscar(entregador.getId());

        entregador.getEndereco().setId(entregadorDB.getEndereco().getId());

        enderecoService.salvar(entregador.getEndereco());

        return repo.save(entregador);
    }

    @Override
    public boolean remover(Long id) {
        buscar(id);
        repo.deleteById(id);

        return true;
    }

    @Override
    public Page<Entregador> listar(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
