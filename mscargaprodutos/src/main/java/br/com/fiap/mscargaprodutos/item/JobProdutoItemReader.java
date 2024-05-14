package br.com.fiap.mscargaprodutos.item;

import br.com.fiap.mscargaprodutos.model.Produto;
import br.com.fiap.mscargaprodutos.repository.ProdutoRepository;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class JobProdutoItemReader {

    private ProdutoRepository produtoRepository;

    @Autowired
    public JobProdutoItemReader(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    @Bean
    public RepositoryItemReader<Produto> produtoItemReader() {
        RepositoryItemReader<Produto> reader = new RepositoryItemReader<>();
        reader.setRepository(produtoRepository);
        reader.setMethodName("findByEnviadoAndHorarioExecucaoBefore");
        reader.setArguments(Arrays.asList("N", LocalDateTime.now()));
        reader.setPageSize(10); // Define o tamanho da página conforme necessário
        reader.setSort(Collections.singletonMap("horarioExecucao", Sort.Direction.ASC));
        return reader;
    }

}
