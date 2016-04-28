package com.main.controller;

import com.main.HomeController;
import com.main.entities.Email;
import com.main.service.MailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    private HomeController controller;
    private MockMvc mockMvc;
    private List<String> toEmails;

    @Mock
    private MailService mailService;

    @Captor
    private ArgumentCaptor<Email> emailCaptor;

    @Before
    public void setup() throws Exception {
        controller = new HomeController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        ReflectionTestUtils.setField(controller, "mailService", mailService);

        toEmails = Arrays.asList("penny1@hotmail.com", "penny2@hotmail.com", "penny3@hotmail.com");
    }

    @Test
    public void showMainPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("main"));
    }

    @Test
    public void sendOneEmail() throws Exception {
        mockMvc.perform(post("/send").param("to", toEmails.get(0))
                .param("topic", "Test Topic")
                .param("body", "Test Email Body"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(instanceOf(List.class))))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(containsInAnyOrder(toEmails.get(0)))));

        verify(mailService).send(emailCaptor.capture());

        Email email = emailCaptor.getValue();
        assertThat(email.getToEmails(), is(containsInAnyOrder(toEmails.get(0))));
        assertThat(email.getTopic(), is("Test Topic"));
        assertThat(email.getBody(), is("Test Email Body"));
    }

    @Test
    public void sendMoreThanOneEmails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/send").param("to", String.join(",", toEmails))
                .param("topic", "Test Topic - Multiple")
                .param("body", "Test Email Body - Multiple"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(instanceOf(List.class))))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(containsInAnyOrder(toEmails.get(0), toEmails.get(1), toEmails.get(2)))));

        verify(mailService).send(emailCaptor.capture());

        Email email = emailCaptor.getValue();
        assertThat(email.getToEmails(), is(containsInAnyOrder(toEmails.get(0), toEmails.get(1), toEmails.get(2))));
        assertThat(email.getTopic(), is("Test Topic - Multiple"));
        assertThat(email.getBody(), is("Test Email Body - Multiple"));
    }

    @Test
    public void sendMoreThanOneEmailsWithNaturalDelimiter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/send").param("to", String.join(", ", toEmails))
                .param("topic", "Test Topic - Multiple")
                .param("body", "Test Email Body - Multiple"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(instanceOf(List.class))))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(containsInAnyOrder(toEmails.get(0), toEmails.get(1), toEmails.get(2)))));

        verify(mailService).send(emailCaptor.capture());

        Email email = emailCaptor.getValue();
        assertThat(email.getToEmails(), is(containsInAnyOrder(toEmails.get(0), toEmails.get(1), toEmails.get(2))));
        assertThat(email.getTopic(), is("Test Topic - Multiple"));
        assertThat(email.getBody(), is("Test Email Body - Multiple"));
    }

    @Test
    public void sendMoreThanOneEmailsAndOneEmailIsEmpty() throws Exception {
        toEmails.set(1, " ");

        mockMvc.perform(MockMvcRequestBuilders.post("/send").param("to", String.join(", ", toEmails))
                .param("topic", "Test Topic - Multiple")
                .param("body", "Test Email Body - Multiple"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(instanceOf(List.class))))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(containsInAnyOrder(toEmails.get(0), toEmails.get(2)))));

        verify(mailService).send(emailCaptor.capture());

        Email email = emailCaptor.getValue();
        assertThat(email.getToEmails(), is(containsInAnyOrder(toEmails.get(0), toEmails.get(2))));
        assertThat(email.getTopic(), is("Test Topic - Multiple"));
        assertThat(email.getBody(), is("Test Email Body - Multiple"));
    }

    @Test
    public void sendMoreThanOneEmailsAndThereIsDuplicatedEmails() throws Exception {
        toEmails.set(1, toEmails.get(0));
        toEmails.set(2, toEmails.get(0));

        mockMvc.perform(MockMvcRequestBuilders.post("/send").param("to", String.join(",", toEmails))
                .param("topic", "Test Topic - Multiple")
                .param("body", "Test Email Body - Multiple"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(instanceOf(List.class))))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(containsInAnyOrder(toEmails.get(0)))));

        verify(mailService).send(emailCaptor.capture());

        Email email = emailCaptor.getValue();
        assertThat(email.getToEmails(), is(containsInAnyOrder(toEmails.get(0))));
        assertThat(email.getTopic(), is("Test Topic - Multiple"));
        assertThat(email.getBody(), is("Test Email Body - Multiple"));
    }

    @Test
    public void sendEmailButMailServiceThrowsError() throws Exception {
        doThrow(new MessagingException("Error")).when(mailService).send(any(Email.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/send").param("to", toEmails.get(0))
                .param("topic", "Test Topic")
                .param("body", "Test Email Body"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", is("Error: Error")));

        verify(mailService).send(emailCaptor.capture());

        Email email = emailCaptor.getValue();
        assertThat(email.getToEmails(), is(containsInAnyOrder(toEmails.get(0))));
        assertThat(email.getTopic(), is("Test Topic"));
        assertThat(email.getBody(), is("Test Email Body"));
    }
}
