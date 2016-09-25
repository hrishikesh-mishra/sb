package com.hrishikeshmishra.sb.batchcamel.apis;

import com.hrishikeshmishra.sb.batchcamel.apis.models.SRDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.hrishikeshmishra.sb.batchcamel.apis.configurations.Constants.CAMEL_VENDOR_ROUTE;
import static com.hrishikeshmishra.sb.batchcamel.apis.configurations.Constants.QUEUE_NAME;

/**
 * Created by hrishikesh.mishra on 24/09/16.
 */
@Component
@Slf4j
public class ScheduledTasks {

    private final StringRedisTemplate redisTemplate;
    private final ProducerTemplate producerTemplate;

    @Autowired
    public ScheduledTasks(StringRedisTemplate redisTemplate, ProducerTemplate producerTemplate) {
        this.redisTemplate = redisTemplate;
        this.producerTemplate = producerTemplate;
    }

    @Scheduled(fixedRate = 500)
    public void confirmVendor() {
        log.info("\n------------------------------------------------------------------------------------------------------------------------------" );
        Long startTime = System.currentTimeMillis();
        List<SRDetail> srDetails = getSRs(2);
        log.info("[ScheduledTasks] starting for  SR : {} " , srDetails );
        if(!Objects.isNull(srDetails)){
            for (SRDetail srDetail : srDetails){
                producerTemplate.requestBody(CAMEL_VENDOR_ROUTE, srDetail);
            }
        }
        pushSR(srDetails);
        log.info("[ScheduledTasks] pushed for  SR : {} " , srDetails );
        Long endTime = System.currentTimeMillis();
        log.info("[ScheduledTasks] ConfirmVendor in {} ms \n\n", (endTime - startTime));
        log.info("\n------------------------------------------------------------------------------------------------------------------------------" );
    }

    private List<SRDetail> getSRs(int srCount){
        List<SRDetail> srList = new ArrayList<>();
        for (int i =0 ; i < srCount; i++){
            SRDetail srDetail = popSR();
            if(Objects.isNull(srDetail)) return srList;
            srList.add(srDetail);
        }
        return srList;
    }

    private SRDetail popSR() {
        ListOperations<String, String> listOp = redisTemplate.opsForList();
        return new SRDetail(listOp.leftPop(QUEUE_NAME));
    }

    private void pushSR(List<SRDetail> srDetails) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        for(SRDetail srDetail : srDetails) {
            listOps.rightPush(QUEUE_NAME, srDetail.getSrId());
        }
    }
}

