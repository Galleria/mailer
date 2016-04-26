package com.main.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * Created by cadet on 4/26/2016 AD.
 */
@Service
public class MailService {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.enableTLS}")
    private boolean enableTls;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    public void send(List<String> emails, String topic, String body) throws MessagingException {

        validate(emails, topic, body);

        Properties prop = new Properties();
        prop.setProperty("mail.smtp.starttls.enable", Boolean.toString(enableTls));

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setJavaMailProperties(prop);

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(emails.toArray(new String[0]));
        helper.setSubject(topic);
        helper.setText(body, true);

        sender.send(message);
    }

    private void validate(List<String> emails, String topic, String body) {
        if (CollectionUtils.isEmpty(emails)) {
            throw new IllegalArgumentException("Emails are required");
        }

        if (emails.size() > 20) {
            throw new IllegalArgumentException("Emails are too much");
        }

        if (!StringUtils.hasText(topic)) {
            throw new IllegalArgumentException("Topic is required");
        }

        if (!StringUtils.hasText(body)) {
            throw new IllegalArgumentException("Body is required");
        }
    }
}
