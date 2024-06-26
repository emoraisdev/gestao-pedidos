package br.com.fiap.mscargaprodutos.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    private JobLauncher jobLauncher;

    private Job job;

    @Autowired
    public JobScheduler(JobLauncher jobLauncher, Job job){
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Scheduled(fixedRateString = "${periodo.execucao.consumo}") // Executa a cada minuto
    public void executeJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("produtoJob", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job, jobParameters);
    }
}