package com.hrishikeshmishra.bj.steps;

import com.hrishikeshmishra.bj.models.Vendor;
import com.hrishikeshmishra.bj.models.VendorRequest;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.Map;

import static com.hrishikeshmishra.bj.models.Constants.DETAIL;
import static com.hrishikeshmishra.bj.models.Constants.QUEUE_NAME;

/**
 * Created by hrishikesh.mishra on 09/09/16.
 */
public class VendorItemReader implements ItemReader<VendorRequest> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOps;

    @Resource(name="redisTemplate")
    private HashOperations<String, String, String> hashOperations;

    @Override
    public VendorRequest read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (listOps.size(QUEUE_NAME) == 0) return null;
        String sr = listOps.leftPop(QUEUE_NAME);
        return new VendorRequest(sr, getVendor(sr));
    }

    private Vendor getVendor(String sr){
        Map<String, String> srDetail = hashOperations.entries(sr.concat(DETAIL));
        return new Vendor(
                    srDetail.get("merchantReferenceId"),
                    srDetail.get("merchantCode"),
                    srDetail.get("sourceType"),
                    Double.parseDouble(srDetail.get("shipmentValue")),
                    Double.parseDouble(srDetail.get("amountToCollect")),
                    Long.parseLong(srDetail.get("originPincode"))
        );
    }
}
