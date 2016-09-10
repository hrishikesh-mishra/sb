package com.hrishikeshmishra.bj.redisdump;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrishikeshmishra.bj.models.Constants;
import com.hrishikeshmishra.bj.models.Vendor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.IOException;

import static com.hrishikeshmishra.bj.models.Constants.QUEUE_NAME;

/**
 * Created by hrishikesh.mishra on 10/09/16.
 */

@SpringBootApplication
@Slf4j
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
public class RedisDumper implements CommandLineRunner {

    private final static String SR_PREFIX = "SR_";
    private final static int LIMIT = 10;
    private final static String REDIS_LOAD = "load";
    private final static String REDIS_CLEAN = "clean";

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public void run(String... args) throws Exception {
        if (isInvalidRedisOperation(args))  {
            System.out.println("\nError: Valid redis operation [load or clean]");
            return;
        }

        if(args[0].equals(REDIS_LOAD))
            writeToRedis();
        else if(args[0].equals(REDIS_CLEAN))
            cleanRedis();
    }

    private boolean isInvalidRedisOperation(String[] args) {
        return args.length == 0 ||
                !(args[0].equals(REDIS_LOAD) || args[0].equals(REDIS_CLEAN));
    }

    private void writeToRedis() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ListOperations<String, String> listOps = this.redisTemplate.opsForList();
        ValueOperations<String, String> valueOps = this.redisTemplate.opsForValue();

        for (int i = 1; i <= LIMIT; i++)
            addSRToRedis(objectMapper, i, listOps, valueOps);

    }

    private void addSRToRedis(ObjectMapper objectMapper, int index, ListOperations<String, String> listOps,  ValueOperations<String, String> valueOps  ) throws JsonProcessingException {
        String sr = SR_PREFIX.concat(String.valueOf(index));
        listOps.leftPush(QUEUE_NAME, sr);
        Vendor vendor = getRandomVendorObject(index);
        valueOps.set(sr.concat(Constants.DETAIL), objectMapper.writeValueAsString(vendor));
        log.info("\nPush SR: {} with Vendor: {} ", sr,  vendor);
    }

    private void cleanRedis() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ListOperations<String, String> listOps = this.redisTemplate.opsForList();
        ValueOperations<String, String> valueOps = this.redisTemplate.opsForValue();

        while (listOps.size(QUEUE_NAME) > 0) {
            removeFromRedis(objectMapper, listOps, valueOps);
        }
    }

    private void removeFromRedis(ObjectMapper objectMapper,  ListOperations<String, String> listOps,  ValueOperations<String, String> valueOps) throws IOException {
        String sr = listOps.rightPop(QUEUE_NAME);
        Vendor vendor = objectMapper.readValue(valueOps.get((sr.concat(Constants.DETAIL))), Vendor.class);
        this.redisTemplate.delete(sr.concat(Constants.DETAIL));
        log.info("\nRemoved SR: {}, with Vendor: {}", sr, vendor);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RedisDumper.class, args).close();
    }

    private Vendor getRandomVendorObject(int index) {
        return new Vendor("merchantReferenceId_" + index,
                "merchantCode_" + index,
                "sourceType_" + index,
                1000 + index,
                5000 + index,
                1100 + index);
    }

}
