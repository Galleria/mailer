package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by cadet on 4/26/2016 AD.
 */
@Controller
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/")
    String home() {
        return "main";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}
