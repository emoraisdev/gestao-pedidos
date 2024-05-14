package br.com.fiap.mscargaprodutos.item;

import br.com.fiap.mscargaprodutos.dto.ProdutoDTO;
import br.com.fiap.mscargaprodutos.model.Produto;
import br.com.fiap.mscargaprodutos.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class JobProdutoItemWriter implements ItemWriter<Produto> {

    private final RestTemplate restTemplate;

    private final ProdutoRepository produtoRepository;

    @Autowired
    public JobProdutoItemWriter(RestTemplate restTemplate, ProdutoRepository produtoRepository) {
        this.restTemplate = restTemplate;
        this.produtoRepository = produtoRepository;
    }


    @Override
    public void write(Chunk<? extends Produto> list) throws Exception {
        try {
            for (Produto produto : list) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Produto> requestEntity = new HttpEntity<>(produto, headers);
                ResponseEntity<ProdutoDTO> response = restTemplate.postForEntity("http://localhost:8080/produtos",requestEntity, ProdutoDTO.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    produto.setEnviado("S");
                    log.info("Chamada HTTP bem-sucedida para o produto: " + produto.getNome());
                }
                produtoRepository.save(produto);
            }
        } catch (Exception e) {
            throw new Exception("Erro durante o processamento do job", e);
        }

    }

}
