package com.hrishikeshmishra.bj.config;

import com.hrishikeshmishra.bj.models.VendorInfo;
import com.hrishikeshmishra.bj.models.VendorRequest;
import com.hrishikeshmishra.bj.steps.VendorItemProcessor;
import com.hrishikeshmishra.bj.steps.VendorItemReader;
import com.hrishikeshmishra.bj.steps.VendorItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hrishikesh.mishra on 10/09/16.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private VendorItemReader vendorItemReader;

    @Autowired
    private VendorItemProcessor vendorItemProcessor;

    @Autowired
    private VendorItemWriter vendorItemWriter;

    @Bean
    public Job vendorConfirmJob() {
        return jobBuilderFactory.get("vendorConfirmJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<VendorRequest, VendorInfo> chunk(1)
                .reader(vendorItemReader)
                .processor(vendorItemProcessor)
                .writer(vendorItemWriter)
                .build();
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
