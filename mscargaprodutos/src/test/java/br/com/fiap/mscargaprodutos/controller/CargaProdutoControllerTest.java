package br.com.fiap.mscargaprodutos.controller;

import br.com.fiap.mscargaprodutos.repository.ProdutoRepository;
import br.com.fiap.mscargaprodutos.service.CSVProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @Test
    void uploadFileProduto_ValidFile_Success() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test,data,10.0,100".getBytes());
        LocalDateTime executionTime = LocalDateTime.now();

        ResponseEntity<String> response = cargaProdutoController.uploadFileProduto(file, executionTime);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Arquivo CSV processado com sucesso.", response.getBody());
        verify(csvProcessorService, times(1)).processarCsv(file, executionTime);
    }

    @Test
    void uploadFileProduto_EmptyFile_BadRequest() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "", "text/csv", "".getBytes());
        LocalDateTime executionTime = LocalDateTime.now();

        ResponseEntity<String> response = cargaProdutoController.uploadFileProduto(file, executionTime);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O arquivo está vazio", response.getBody());
        verify(csvProcessorService, never()).processarCsv(any(), any());
    }

    @Test
    void testUploadFileProdutoThrowsException() throws Exception {
        MultipartFile mockFile = mock(MultipartFile.class);
        LocalDateTime executionTime = LocalDateTime.now();

        when(mockFile.isEmpty()).thenReturn(false);
        doThrow(new RuntimeException("Simulated exception")).when(csvProcessorService).processarCsv(mockFile, executionTime);

        ResponseEntity<String> response = cargaProdutoController.uploadFileProduto(mockFile, executionTime);

        assertEquals(ResponseEntity.status(500).body("Erro ao processar o arquivo CSV: Simulated exception"), response);
    }
}