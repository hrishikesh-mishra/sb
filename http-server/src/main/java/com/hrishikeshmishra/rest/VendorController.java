package com.hrishikeshmishra.rest;

import com.hrishikeshmishra.rest.models.VendorRequest;
import com.hrishikeshmishra.rest.models.VendorResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by hrishikesh.mishra on 10/09/16.
 */
@RestController
@RequestMapping("/vendor")
public class VendorController {

    private final Random randomGenerator;

    public VendorController() {
        this.randomGenerator = new Random();
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public VendorResponse confirmVendor(@RequestBody VendorRequest request) {
        return getVendorResponse();
    }

    private VendorResponse getVendorResponse(){
        int rand = randomGenerator.nextInt(3);
        switch (rand){
            case 0:
                return new VendorResponse("NOT_CHANGED");
            case 1:
                return new VendorResponse("CHANGED" , "new vendor");
            default:
                return new VendorResponse("VNF");
        }
    }

}
