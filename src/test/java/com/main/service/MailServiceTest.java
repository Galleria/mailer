package com.main.service;

import com.main.entities.Email;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class MailServiceTest {

    private static final String HOST = "localhost";
    private static final int PORT = 2500;
    private static final String FROM = "gadget@hotmail.com";

    private MailService mailService;
    private Wiser wiser;
    private Email email;

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

        List<String> toEmails = Arrays.asList("penny1@hotmail.com", "penny2@hotmail.com", "penny3@hotmail.com");
        email = new Email(toEmails, "Test", "This is a test email");
    }

    @After
    public void tearDown() {
        wiser.stop();
    }

    @Test
    public void sendOneEmail() throws Exception {
        email.setToEmails(Arrays.asList("penny@hotmail.com"));

        mailService.send(email);

        List<WiserMessage> messages = wiser.getMessages();

        assertThat(messages, hasSize(1));
        assertThatEmailMessageIsCorrect(messages.get(0), email.getToEmails().get(0), email.getTopic(), email.getBody());
    }

    @Test
    public void sendMultipleEmails() throws Exception {
        mailService.send(email);

        assertThat(wiser.getMessages(), hasSize(3));

        for (int i = 0; i < wiser.getMessages().size(); i++) {
            WiserMessage message = wiser.getMessages().get(i);
            assertThatEmailMessageIsCorrect(message, email.getToEmails().get(i), email.getTopic(), email.getBody());
        }
    }

    @Test
    public void sendEmailWithNullTopic() throws Exception {
        email.setTopic(null);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Topic is required"));

        mailService.send(email);
    }

    @Test
    public void sendEmailWithBlankTopic() throws Exception {
        email.setTopic("");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Topic is required"));

        mailService.send(email);
    }

    @Test
    public void sendEmailWithNullBody() throws Exception {
        email.setBody(null);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Body is required"));

        mailService.send(email);
    }

    @Test
    public void sendEmailWithBlankBody() throws Exception {
        email.setBody("");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Body is required"));

        mailService.send(email);
    }

    @Test
    public void sendEmailWithNullToEmails() throws Exception {
        email.setToEmails(null);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Emails are required"));

        mailService.send(email);
    }

    @Test
    public void sendEmailWithEmptyToEmails() throws Exception {
        email.setToEmails(Collections.emptyList());
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Emails are required"));

        mailService.send(email);
    }

    @Test
    public void sendEmailsMoreThanTwentyEmails() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Emails are too much"));

        List<String> toEmails = new ArrayList<String>(20);
        for (int i = 0; i < 21; i++) {
            toEmails.add("penny" + i + "@hotmail.com");
        }

        email.setToEmails(toEmails);

        mailService.send(email);
    }

    private String getMessageBody(MimeMessage message) throws IOException, MessagingException {
        Multipart multipart = (Multipart) message.getContent();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        multipart.writeTo(out);

        return new String(out.toByteArray(), "UTF-8");
    }

    private void assertThatEmailMessageIsCorrect(WiserMessage actualMessage, String expectedToEmail, String expectedTopic, String expectedBody) throws MessagingException, IOException {
        MimeMessage mimeMessage = actualMessage.getMimeMessage();

        assertThat(actualMessage.getEnvelopeSender(), is(FROM));
        assertThat(actualMessage.getEnvelopeReceiver(), is(expectedToEmail));
        assertThat(mimeMessage.getSubject(), is(expectedTopic));
        assertThat(getMessageBody(mimeMessage), is(containsString(expectedBody)));
    }
}
