package br.com.fiap.mscargaprodutos.service;

import br.com.fiap.mscargaprodutos.model.Produto;
import br.com.fiap.mscargaprodutos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

@Service
public class CSVProcessorService {

    private ProdutoRepository produtoRepository;

    @Value("${delimiter.file}")
    private String delimiterFile;

    @Autowired
    public CSVProcessorService(ProdutoRepository produtoRepository, String delimiterFile){
        this.produtoRepository = produtoRepository;
        this.delimiterFile = delimiterFile;
    }


    public void processarCsv(MultipartFile file, LocalDateTime localDateTime) throws IOException {
        InputStream inputStream = file.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] lineSplited = line.split(delimiterFile);
            Produto produto = Produto.builder()
                    .nome(lineSplited[0])
                    .descricao(lineSplited[1])
                    .preco(Double.parseDouble(lineSplited[2]))
                    .quantidadeEmEstoque(Integer.valueOf(lineSplited[3]))
                    .horarioExecucao(localDateTime)
                    .enviado("N")
                    .build();

            produtoRepository.save(produto);
        }

        reader.close();
    }
}
