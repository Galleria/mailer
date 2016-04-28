package com.main.service;

import com.main.entities.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

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

    public void send(Email email) throws MessagingException {
        validate(email);
        JavaMailSender sender = createMailSender();
        MimeMessage message = createMessage(sender, email);

        sender.send(message);
    }

    private void validate(Email email) {
        if (CollectionUtils.isEmpty(email.getToEmails())) {
            throw new IllegalArgumentException("Emails are required");
        }

        if (email.getToEmails().size() > 20) {
            throw new IllegalArgumentException("Emails are too much");
        }

        if (!StringUtils.hasText(email.getTopic())) {
            throw new IllegalArgumentException("Topic is required");
        }

        if (!StringUtils.hasText(email.getBody())) {
            throw new IllegalArgumentException("Body is required");
        }
    }

    private JavaMailSender createMailSender() {
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.starttls.enable", Boolean.toString(enableTls));

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setJavaMailProperties(prop);

        return sender;
    }

    private MimeMessage createMessage(JavaMailSender targetSender, Email email) throws MessagingException {
        MimeMessage message = targetSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(email.getToEmails().toArray(new String[0]));
        helper.setSubject(email.getTopic());
        helper.setText(email.getBody(), true);

        return message;
    }
}
