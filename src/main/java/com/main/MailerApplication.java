package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

/**
 * Created by cadet on 4/26/2016 AD.
 */
@ComponentScan("com.main")
@EnableAutoConfiguration
@SpringBootApplication
public class MailerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MailerApplication.class, args);
    }
}
