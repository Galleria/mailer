package com.main.service;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.test.util.ReflectionTestUtils;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cadet on 4/26/2016 AD.
 */
public class MailServiceTest {

    private static final String HOST = "localhost";
    private static final int PORT = 2500;
    private static final String FROM = "gadget@hotmail.com";

    private MailService mailService;
    private Wiser wiser;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        wiser = new Wiser();
        wiser.setPort(PORT);
        wiser.start();

        mailService = new MailService();
        ReflectionTestUtils.setField(mailService, "host", HOST);
        ReflectionTestUtils.setField(mailService, "port", PORT);
        ReflectionTestUtils.setField(mailService, "from", FROM);
    }

    @After
    public void tearDown() {
        wiser.stop();
    }

    @Test
    public void testSendOneEmail() throws Exception {
        mailService.send(Arrays.asList("penny@hotmail.com"), "Test", "This is a test email");

        List<WiserMessage> messages = wiser.getMessages();

        assertThat(messages, hasSize(1));
        assertThatEmailMessageIsCorrect(messages.get(0), "penny@hotmail.com", "Test", "This is a test email");
    }

    @Test
    public void testSendMutipleEmails() throws Exception {
        List<String> emails = Arrays.asList("penny1@hotmail.com", "penny2@hotmail.com", "penny3@hotmail.com");

        mailService.send(emails, "Test Multiple", "This is a test email that send to multiple recipients");

        assertThat(wiser.getMessages(), hasSize(3));

        for(int i = 0; i < wiser.getMessages().size(); i++) {
            WiserMessage message = wiser.getMessages().get(i);
            assertThatEmailMessageIsCorrect(message, emails.get(i), "Test Multiple", "This is a test email that send to multiple recipients");
        }
    }

    @Test
    public void testSendEmailWithoutTopic_Null() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Topic is required"));

        mailService.send(Arrays.asList("penny@hotmail.com"), null, "This is a test email");
    }

    @Test
    public void testSendEmailWithoutTopic_Blank() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Topic is required"));

        mailService.send(Arrays.asList("penny@hotmail.com"), "", "This is a test email");
    }

    @Test
    public void testSendEmailWithoutBody_Null() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Body is required"));

        mailService.send(Arrays.asList("penny@hotmail.com"), "Test", null);
    }

    @Test
    public void testSendEmailWithoutBody_Blank() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Body is required"));

        mailService.send(Arrays.asList("penny@hotmail.com"), "Test", "");
    }

    @Test
    public void testSendEmailWithoutEmails_Null() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Emails are required"));

        mailService.send(null, "Test", "This is a test email");
    }

    @Test
    public void testSendEmailWithoutEmails_Empty() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Emails are required"));

        mailService.send(new ArrayList<String>(), "Test", "This is a test email");
    }

    @Test
    public void testSendEmailsMoreThanTwenty() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Emails are too much"));

        List<String> emails = new ArrayList<String>(20);
        for(int i = 0; i < 21; i++) {
            emails.add("penny" + i + "@hotmail.com");
        }

        mailService.send(emails, "Test", "This is a test email");
    }

    private String getMessageBody(MimeMessage message) throws IOException, MessagingException {
        Multipart multipart = (Multipart) message.getContent();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        multipart.writeTo(out);

        return new String(out.toByteArray(), "UTF-8");
    }

    private void assertThatEmailMessageIsCorrect(WiserMessage message, String toEmail, String topic, String body) throws MessagingException, IOException {
        MimeMessage mimeMessage = message.getMimeMessage();

        assertThat(message.getEnvelopeSender(), is(FROM));
        assertThat(message.getEnvelopeReceiver(), is(toEmail));
        assertThat(mimeMessage.getSubject(), is(topic));
        assertThat(getMessageBody(mimeMessage), is(containsString(body)));
    }
}
