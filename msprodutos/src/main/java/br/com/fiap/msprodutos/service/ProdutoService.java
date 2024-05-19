package br.com.fiap.msprodutos.service;

import br.com.fiap.msprodutos.dto.ProdutoEstoqueDTO;
import br.com.fiap.msprodutos.model.Produto;
import br.com.fiap.msprodutos.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Optional<Produto> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Optional<Produto> produtoExistenteOpt = produtoRepository.findById(id);
        if (produtoExistenteOpt.isPresent()) {
            Produto produtoExistente = produtoExistenteOpt.get();
            produtoExistente.setNome(produtoAtualizado.getNome());
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
            produtoExistente.setQuantidadeEmEstoque(produtoAtualizado.getQuantidadeEmEstoque());
            produtoExistente.setPreco(produtoAtualizado.getPreco());
            return produtoRepository.save(produtoExistente);
        }
        return null;
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    @Transactional
    public void diminuirEstoque(List<ProdutoEstoqueDTO> produtosSolicitados) throws BadRequestException {
        for (ProdutoEstoqueDTO produtoSolicitado : produtosSolicitados) {
            Produto produto = produtoRepository.findById(produtoSolicitado.id()).orElseThrow();

            if(produtoSolicitado.quantidade() > produto.getQuantidadeEmEstoque()){
                throw new BadRequestException("Produto solicitado não está disponível");
            }
            produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque()-produtoSolicitado.quantidade());
            produtoRepository.save(produto);
        }
    }
}
