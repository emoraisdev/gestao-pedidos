package br.com.fiap.mscargaprodutos.model;

import br.com.fiap.mscargaprodutos.dto.ProdutoDTO;
import br.com.fiap.mscargaprodutos.item.JobProdutoItemWriter;
import br.com.fiap.mscargaprodutos.listener.CustomJobProdutoExecutionListener;
import br.com.fiap.mscargaprodutos.service.CSVProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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

    }
}
