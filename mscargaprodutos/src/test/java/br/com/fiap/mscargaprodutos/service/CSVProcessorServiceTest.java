package br.com.fiap.mscargaprodutos.service;

import br.com.fiap.mscargaprodutos.repository.ProdutoRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CSVProcessorServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private CSVProcessorService csvProcessorService;

    @BeforeEach
    public void setUp() {
        csvProcessorService = new CSVProcessorService(produtoRepository, ",");
    }


    @Test
    void processarCsv_ValidFile_Success() throws IOException {

        String fileContent = "Produto A,teste,10.00,3";
        byte[] contentBytes = fileContent.getBytes(StandardCharsets.UTF_8);

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", contentBytes);
        LocalDateTime executionTime = LocalDateTime.now();

        csvProcessorService.processarCsv(file, executionTime);

        verify(produtoRepository, times(1)).save(any());
    }
}