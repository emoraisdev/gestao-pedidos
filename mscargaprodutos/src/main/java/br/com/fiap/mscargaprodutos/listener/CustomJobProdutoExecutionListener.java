package br.com.fiap.mscargaprodutos.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CustomJobProdutoExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("<<<<<INICIO DO JOB ID>>>>> {}, horario de inicio: {} ", jobExecution.getJobId(), jobExecution.getStartTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("<<<<<FIM DO JOB ID>>>>> {}, executado com status: {}, inicio: {}, fim: {} ", jobExecution.getJobId(), jobExecution.getExitStatus(), jobExecution.getStartTime(), jobExecution.getEndTime());
    }
}
