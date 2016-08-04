package com.hrishikeshmishra.sb.restcall;

import com.hrishikeshmishra.sb.restcall.entities.Quote;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


@Controller
public class MyController {

    @RequestMapping("/getQ")
    public @ResponseBody
    Quote detectDevice(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);

    }
}
