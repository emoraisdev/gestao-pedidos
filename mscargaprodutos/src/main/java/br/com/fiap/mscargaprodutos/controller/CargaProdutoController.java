package br.com.fiap.mscargaprodutos.controller;


import br.com.fiap.mscargaprodutos.service.CSVProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/csv")
public class CargaProdutoController {

        private CSVProcessorService CSVProcessorService;

        @Autowired
        public CargaProdutoController(CSVProcessorService CSVProcessorService) {
                this.CSVProcessorService = CSVProcessorService;
        }

        @PostMapping("/upload")
        public ResponseEntity<String> uploadFileProduto(@RequestParam("file") MultipartFile file, @RequestParam("executionTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime executionTime) {
                if (file.isEmpty()) {
                        return ResponseEntity.badRequest().body("O arquivo está vazio");
                }

                try {
                        CSVProcessorService.processarCsv(file,executionTime);
                        return ResponseEntity.ok("Arquivo CSV processado com sucesso.");
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o arquivo CSV: " + e.getMessage());
                }
        }
}
