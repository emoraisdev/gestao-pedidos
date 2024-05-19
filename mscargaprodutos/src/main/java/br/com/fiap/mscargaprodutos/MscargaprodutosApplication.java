package br.com.fiap.mscargaprodutos;

import br.com.fiap.mscargaprodutos.listener.CustomJobProdutoExecutionListener;
import br.com.fiap.mscargaprodutos.model.Produto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
@Slf4j
public class MscargaprodutosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscargaprodutosApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public JobExecutionListener jobExecutionListener() {
		return new CustomJobProdutoExecutionListener();
	}
}
