package br.com.fiap.mslogistica.service;

import br.com.fiap.mslogistica.model.Endereco;

public interface EnderecoService {

    Endereco salvar(Endereco endereco);

    Endereco buscar(Long id);

    Endereco alterar(Endereco endereco);

    boolean remover(Long id);
}
