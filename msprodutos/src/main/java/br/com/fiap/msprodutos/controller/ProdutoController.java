package br.com.fiap.msprodutos.controller;


import br.com.fiap.msprodutos.dto.ProdutoEstoqueDTO;
import br.com.fiap.msprodutos.model.Produto;
import br.com.fiap.msprodutos.service.ProdutoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto) {
        return produtoService.criarProduto(produto);
    }

    @GetMapping("/{id}")
    public Optional<Produto> buscarProdutoPorId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id);
    }

    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        return produtoService.atualizarProduto(id, produto);
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
    }

    @PutMapping("/atualizar-estoque")
    public void atualizarEstoque(@RequestBody List<ProdutoEstoqueDTO> produtos) throws BadRequestException {
        produtoService.diminuirEstoque(produtos);
    }

}
