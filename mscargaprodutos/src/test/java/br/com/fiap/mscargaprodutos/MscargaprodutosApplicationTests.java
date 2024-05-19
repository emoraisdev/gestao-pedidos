package br.com.fiap.mscargaprodutos;

import br.com.fiap.mscargaprodutos.repository.ProdutoRepository;
import br.com.fiap.mscargaprodutos.service.CSVProcessorService;
import br.com.fiap.mscargaprodutos.controller.CargaProdutoController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class MscargaprodutosApplicationTests {
	@MockBean
	private CSVProcessorService csvProcessorService;

	@MockBean
	private ProdutoRepository produtoRepository;

	@Autowired
	private CargaProdutoController cargaProdutoController;

	@Test
	void contextLoads() {
	}

}
