package br.com.fiap.msprodutos.controller;

import br.com.fiap.msprodutos.model.Produto;
import br.com.fiap.msprodutos.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    @Test
    void listarProdutos_DeveRetornarListaDeProdutos() throws Exception {
        Produto produto1 = new Produto(1L, "Produto1", "Descricao1", 10, 100.0);
        Produto produto2 = new Produto(2L, "Produto2", "Descricao2", 20, 200.0);
        when(produtoService.listarProdutos()).thenReturn(Arrays.asList(produto1, produto2));

        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Produto1"))
                .andExpect(jsonPath("$[1].nome").value("Produto2"));

        verify(produtoService).listarProdutos();
    }

    @Test
    void criarProduto_DeveRetornarProdutoCriado() throws Exception {
        Produto produto = new Produto(1L, "Novo Produto", "Descricao", 10, 100.0);
        when(produtoService.criarProduto(any(Produto.class))).thenReturn(produto);

        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo Produto"));

        verify(produtoService).criarProduto(any(Produto.class));
    }

    @Test
    void buscarProdutoPorId_DeveRetornarProduto_QuandoProdutoExistir() throws Exception {
        Produto produto = new Produto(1L, "Produto", "Descricao", 10, 100.0);
        when(produtoService.buscarProdutoPorId(1L)).thenReturn(Optional.of(produto));

        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Produto"));

        verify(produtoService).buscarProdutoPorId(1L);
    }

    @Test
    void atualizarProduto_DeveRetornarProdutoAtualizado() throws Exception {
        Produto produtoAtualizado = new Produto(1L, "Produto Atualizado", "Descricao Atualizada", 20, 200.0);
        when(produtoService.atualizarProduto(eq(1L), any(Produto.class))).thenReturn(produtoAtualizado);

        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
        mockMvc.perform(put("/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(produtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Atualizado"));

        verify(produtoService).atualizarProduto(eq(1L), any(Produto.class));
    }

    @Test
    void deletarProduto_DeveRetornarStatusOk() throws Exception {
        doNothing().when(produtoService).deletarProduto(1L);

        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isOk());

        verify(produtoService).deletarProduto(1L);
    }
}

