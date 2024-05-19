package br.com.fiap.mscargaprodutos.listener;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.batch.core.JobExecution;

import java.time.LocalDateTime;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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