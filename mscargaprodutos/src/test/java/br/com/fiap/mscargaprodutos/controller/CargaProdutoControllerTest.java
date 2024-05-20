package br.com.fiap.mscargaprodutos.controller;

import br.com.fiap.mscargaprodutos.repository.ProdutoRepository;
import br.com.fiap.mscargaprodutos.service.CSVProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CargaProdutoControllerTest {

    @Mock
    private CSVProcessorService csvProcessorService;

    @InjectMocks
    private CargaProdutoController cargaProdutoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadFileProduto_Success() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        LocalDateTime executionTime = LocalDateTime.now();

        doNothing().when(csvProcessorService).processarCsv(file, executionTime);

        ResponseEntity<String> response = cargaProdutoController.uploadFileProduto(file, executionTime);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Arquivo CSV processado com sucesso.", response.getBody());
    }

    @Test
    void testUploadFileProduto_InvalidFileType() {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());
        LocalDateTime executionTime = LocalDateTime.now();

        ResponseEntity<String> response = cargaProdutoController.uploadFileProduto(file, executionTime);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("O arquivo em formato invalido", response.getBody());
    }

    @Test
    void testUploadFileProduto_EmptyFile() {
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", new byte[0]);
        LocalDateTime executionTime = LocalDateTime.now();

        ResponseEntity<String> response = cargaProdutoController.uploadFileProduto(file, executionTime);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("O arquivo está vazio", response.getBody());
    }

    @Test
    void testUploadFileProduto_ProcessingError() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        LocalDateTime executionTime = LocalDateTime.now();

        doThrow(new RuntimeException("Processing failed")).when(csvProcessorService).processarCsv(file, executionTime);

        ResponseEntity<String> response = cargaProdutoController.uploadFileProduto(file, executionTime);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Erro ao processar o arquivo CSV: Processing failed", response.getBody());
    }
}