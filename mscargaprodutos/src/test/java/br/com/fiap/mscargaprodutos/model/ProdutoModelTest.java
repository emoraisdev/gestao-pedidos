package br.com.fiap.mscargaprodutos.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProdutoModelTest {

    @InjectMocks
    private Produto produto;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void testProdutoSuccess() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setDescricao("Descricao Teste");
        produto.setPreco(1.99);
        produto.setHorarioExecucao(LocalDateTime.now());
        produto.setEnviado("N");
        produto.setQuantidadeEmEstoque(1);

        assertEquals(produto, produto);
    }
}
