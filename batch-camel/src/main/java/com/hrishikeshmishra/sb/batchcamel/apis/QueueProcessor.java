package com.hrishikeshmishra.sb.batchcamel.apis;

import com.hrishikeshmishra.sb.batchcamel.apis.models.SRDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import static com.hrishikeshmishra.sb.batchcamel.apis.configurations.Constants.CAMEL_VENDOR_ROUTE;
import static com.hrishikeshmishra.sb.batchcamel.apis.configurations.Constants.QUEUE_NAME;

/**
 * Created by hrishikesh.mishra on 24/09/16.
 */
@Component
@Slf4j
public class QueueProcessor  implements Runnable  {


    private final StringRedisTemplate redisTemplate;
    private final ProducerTemplate producerTemplate;

    @Autowired
    public QueueProcessor(StringRedisTemplate  redisTemplate, ProducerTemplate producerTemplate) {
        this.redisTemplate = redisTemplate;
        this.producerTemplate = producerTemplate;
    }



//    public class VendorServiceInvoker implements Runnable {

        @Override
        public void run() {
            while (true){
                SRDetail srDetail = popSR();
                log.info("[VendorServiceInvoker] starting for  SR : {} " , srDetail );
                producerTemplate.requestBody(CAMEL_VENDOR_ROUTE, srDetail);
                pushSR(srDetail);
                log.info("[VendorServiceInvoker] pushed for  SR : {} " , srDetail );
            }
        }

        private SRDetail popSR() {
            BoundListOperations<String, String> blOps= redisTemplate.boundListOps(QUEUE_NAME);
            String sr = blOps.leftPop();
            return new SRDetail(sr);
        }

        private void pushSR(SRDetail srDetail){
            BoundListOperations<String, String> blOps= redisTemplate.boundListOps(QUEUE_NAME);
            blOps.rightPush(srDetail.getSrId());
        }
//    }

}
