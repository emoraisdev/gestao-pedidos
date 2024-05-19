package br.com.fiap.mscargaprodutos.item;

import br.com.fiap.mscargaprodutos.dto.ProdutoDTO;
import br.com.fiap.mscargaprodutos.model.Produto;
import br.com.fiap.mscargaprodutos.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.batch.item.Chunk;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobProdutoItemWriterTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private JobProdutoItemWriter jobProdutoItemWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jobProdutoItemWriter, "urlProduto", "http://teste.com");
    }

    @Test
    void testWriteSuccess() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setId(1L);
        produto.setDescricao("Descricao Teste");
        produto.setPreco(1.99);
        produto.setHorarioExecucao(LocalDateTime.now());
        produto.setEnviado("N");
        produto.setQuantidadeEmEstoque(1);

        ResponseEntity<ProdutoDTO> responseEntity = new ResponseEntity<>(new ProdutoDTO(), HttpStatus.OK);

        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(ProdutoDTO.class)))
                .thenReturn(responseEntity);

        jobProdutoItemWriter.write(new Chunk<>(Arrays.asList(produto)));

        assertEquals("S", produto.getEnviado());
        verify(produtoRepository, times(1)).save(produto);
        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(ProdutoDTO.class));
    }

    @Test
    void testWriteFailure() {
        Produto produto = new Produto();
        produto.setNome("Produto Teste");

        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(ProdutoDTO.class)))
                .thenThrow(new RuntimeException("exception"));

        Exception exception = assertThrows(Exception.class, () -> {
            jobProdutoItemWriter.write(new Chunk<>(Arrays.asList(produto)));
        });

        assertTrue(exception.getMessage().contains("Erro durante o processamento do job"));
        verify(produtoRepository, never()).save(produto);
    }
}
