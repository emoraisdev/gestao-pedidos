package br.com.fiap.msprodutos.service;
import br.com.fiap.msprodutos.model.Produto;
import br.com.fiap.msprodutos.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void listarProdutos_DeveRetornarListaDeProdutos() {
        Produto produto1 = new Produto(1L, "Produto1", "Descricao1", 10, 100.0);
        Produto produto2 = new Produto(2L, "Produto2", "Descricao2", 20, 200.0);
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1, produto2));

        List<Produto> produtos = produtoService.listarProdutos();

        assertThat(produtos).hasSize(2);
        verify(produtoRepository).findAll();
    }

    @Test
    void criarProduto_DeveRetornarProdutoCriado() {
        Produto produto = new Produto();
        produto.setNome("Novo Produto");
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto salvo = produtoService.criarProduto(produto);

        assertThat(salvo).isNotNull();
        verify(produtoRepository).save(produto);
    }

    @Test
    void buscarProdutoPorId_DeveRetornarProduto_QuandoProdutoExistir() {
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Optional<Produto> encontrado = produtoService.buscarProdutoPorId(1L);

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getId()).isEqualTo(1L);
        verify(produtoRepository).findById(1L);
    }

    @Test
    void atualizarProduto_DeveRetornarProdutoAtualizado() {
        Produto produtoExistente = new Produto(1L, "Produto1", "Descricao1", 10, 100.0);
        Produto produtoAtualizado = new Produto(null, "Produto Atualizado", "Descricao Atualizada", 20, 200.0);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoExistente));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoExistente);

        Produto atualizado = produtoService.atualizarProduto(1L, produtoAtualizado);

        assertThat(atualizado.getNome()).isEqualTo("Produto Atualizado");
        assertThat(atualizado.getDescricao()).isEqualTo("Descricao Atualizada");
        verify(produtoRepository).findById(1L);
        verify(produtoRepository).save(produtoExistente);
    }

    @Test
    void deletarProduto_DeveChamarDeleteById() {
        produtoService.deletarProduto(1L);

        verify(produtoRepository).deleteById(1L);
    }
}
