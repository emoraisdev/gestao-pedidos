package br.com.fiap.mspedidos.integracao.msprodutos.dto;

public record ProdutoEstoqueResponseDTO(Long id, String nome, Integer quantidadeEstoque, Double preco) {
}
