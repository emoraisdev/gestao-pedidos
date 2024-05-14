package br.com.fiap.mscargaprodutos.config;


import br.com.fiap.mscargaprodutos.item.JobProdutoItemWriter;
import br.com.fiap.mscargaprodutos.listener.CustomJobProdutoExecutionListener;
import br.com.fiap.mscargaprodutos.listener.CustomProdutoItemWriteListener;
import br.com.fiap.mscargaprodutos.model.Produto;
import br.com.fiap.mscargaprodutos.repository.ProdutoRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;

@Configuration
public class JobCargaProdutosBatchConfig {

	private ProdutoRepository produtoRepository;
	private JobProdutoItemWriter jobProdutoItemWriter;
	private CustomProdutoItemWriteListener customProdutoItemWriteListener;
	private CustomJobProdutoExecutionListener customJobProdutoExecutionListener;


	@Autowired
	public JobCargaProdutosBatchConfig(JobProdutoItemWriter jobProdutoItemWriter,
									   CustomProdutoItemWriteListener customProdutoItemWriteListener, CustomJobProdutoExecutionListener customJobProdutoExecutionListener, ProdutoRepository produtoRepository){
		this.jobProdutoItemWriter = jobProdutoItemWriter;
		this.customProdutoItemWriteListener = customProdutoItemWriteListener;
		this.customJobProdutoExecutionListener = customJobProdutoExecutionListener;
		this.produtoRepository = produtoRepository;
	}

	@Bean
	public ItemReader<Produto> reader() {
		RepositoryItemReader<Produto> reader = new RepositoryItemReader<>();
		reader.setRepository(produtoRepository);
		reader.setMethodName("findByEnviadoAndHorarioExecucaoBefore");
		reader.setArguments(Arrays.asList("N", LocalDateTime.now()));
		reader.setPageSize(10); // Define o tamanho da página conforme necessário
		reader.setMaxItemCount(10); // Define o número máximo de itens a serem lidos
		HashMap<String, Sort.Direction> sorts = new HashMap<>();
		sorts.put("id", Sort.Direction.ASC);
		reader.setSort(sorts);

		return reader;
	}

	@Bean
	public Step buildProdutoStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return  new StepBuilder("step1", jobRepository)
				.<Produto, Produto> chunk(100, transactionManager)
				.reader(reader())
				.writer(jobProdutoItemWriter)
				.listener(customProdutoItemWriteListener)
				.build();
	}

	@Bean("produtoJob")
	public Job cargaProdutosJob(JobRepository jobRepository, Step buildProdutoStep) {
		return new JobBuilder("jobCargaProduto", jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(customJobProdutoExecutionListener)
				.flow(buildProdutoStep)
				.end()
				.build();
	}

}
