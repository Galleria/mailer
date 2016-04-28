package com.main;

import com.main.entities.Email;
import com.main.form.EmailForm;
import com.main.service.MailService;
import org.robotframework.javalib.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private static final String MAIL_DELIMITER = "\\s*,\\s*";

    @Autowired
    private MailService mailService;

    @RequestMapping("/")
    String home() {
        return "main";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ModelAndView send(EmailForm form) {
        List<String> emails = getEmails(form.getTo());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");

        try {
            Email email = new Email(getEmails(form.getTo()), form.getTopic(), form.getBody());
            mailService.send(email);
            modelAndView.getModel().put("to", emails);
        } catch (Exception e) {
            modelAndView.getModel().put("message", "Error: " + e.getMessage());
        }

        return modelAndView;
    }

    private List<String> getEmails(String to) {
        List<String> emails = Arrays.asList(to.split(MAIL_DELIMITER));
        return emails.stream().filter(StringUtils::hasText).distinct().collect(Collectors.toList());
    }
}
