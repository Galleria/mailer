package com.main;

import com.main.entities.Contact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cadet on 4/26/2016 AD.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    String home() {
        return "main";
    }
}
