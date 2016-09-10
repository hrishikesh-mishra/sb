package com.hrishikeshmishra.bj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by hrishikesh.mishra on 10/09/16.
 */
@SpringBootApplication(excludeName = {"com.hrishikeshmishra.bj.redisdump.RedisDumper"} )

//@EnableAutoConfiguration(exclude = {
//        DataSourceAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class
//})
public class BJApp {
    public static void main(String[] args) {
        SpringApplication.run(BJApp.class, args);
    }
}
