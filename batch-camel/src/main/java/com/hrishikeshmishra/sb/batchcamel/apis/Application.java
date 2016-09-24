package com.hrishikeshmishra.sb.batchcamel.apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by hrishikesh.mishra on 24/09/16.
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//        QueueProcessor queueProcessor = context.getBean(QueueProcessor.class);
//        Thread t = new Thread(queueProcessor);
//        t.start();
//        t.join();
    }

}
