package br.com.fiap.mscargaprodutos.listener;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import java.time.LocalDateTime;

class CustomJobProdutoExecutionListenerTest {

    @Test
    void testBeforeJob() {
        JobExecution jobExecution = new JobExecution(1L);
        CustomJobProdutoExecutionListener listener = new CustomJobProdutoExecutionListener();
        listener.beforeJob(jobExecution);
    }

    @Test
    void testAfterJob() {
        JobExecution jobExecution = new JobExecution(1L);
        jobExecution.setEndTime(LocalDateTime.now());
        CustomJobProdutoExecutionListener listener = new CustomJobProdutoExecutionListener();
        listener.afterJob(jobExecution);
    }
}