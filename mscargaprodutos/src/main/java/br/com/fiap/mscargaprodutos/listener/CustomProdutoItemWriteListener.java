package br.com.fiap.mscargaprodutos.listener;


import br.com.fiap.mscargaprodutos.model.Produto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class CustomProdutoItemWriteListener implements ItemWriteListener<Produto> {


    public void beforeWrite(List<? extends Produto> list) {
        for (Produto produto: list) {
            log.info("before:", produto.getNome());
        }
    }

    public void afterWrite(List<? extends Produto> list) {
        for (Produto produto: list) {
            log.info("after:", produto.getNome());
        }

    }

    public void onWriteError(Exception e, List<? extends Produto> list) {
        log.error("Error ao escrever produto:", e.getCause());
        log.error("Exception:", e.getCause());
    }
}
