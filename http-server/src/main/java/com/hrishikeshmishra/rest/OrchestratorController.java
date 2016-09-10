package com.hrishikeshmishra.rest;

import com.hrishikeshmishra.rest.models.VNFRequest;
import com.hrishikeshmishra.rest.models.VendorChangeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hrishikesh.mishra on 10/09/16.
 */
@RestController
@RequestMapping("/orchestrator/vendor")
public class OrchestratorController {


    @RequestMapping(value = "/vnf", method = RequestMethod.POST)
    public ResponseEntity<String> handleVNF(@RequestBody VNFRequest request) {
        return new ResponseEntity<>("Ok!", HttpStatus.OK);
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public ResponseEntity<String> handleVendorChange(@RequestBody VendorChangeRequest request) {
        return new ResponseEntity<>("Ok!", HttpStatus.OK);
    }

}
