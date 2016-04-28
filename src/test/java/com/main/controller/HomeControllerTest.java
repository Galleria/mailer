package com.main.controller;

import com.main.HomeController;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Created by cadet on 4/27/2016 AD.
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    private HomeController controller;
    private MockMvc mockMvc;
    private List<String> emails;

    @Mock
    private MailService mailService;

    @Captor
    private ArgumentCaptor<List> emailCaptor;

    @Before
    public void setup() throws Exception {
        controller = new HomeController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        ReflectionTestUtils.setField(controller, "mailService", mailService);

        emails = Arrays.asList("penny1@hotmail.com", "penny2@hotmail.com", "penny3@hotmail.com");
    }

    @Test
    public void testShowMainPage() throws Exception {
        mockMvc.perform(get("/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("main"));
    }

    @Test
    public void testSendOneEmail() throws Exception {
        mockMvc.perform(post("/send").param("to", emails.get(0))
                .param("topic", "Test Topic")
                .param("body", "Test Email Body"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(instanceOf(List.class))))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(containsInAnyOrder(emails.get(0)))));

        verify(mailService).send(emailCaptor.capture(), eq("Test Topic"), eq("Test Email Body"));
        assertThat(emailCaptor.getValue().toArray(new String[0]), is(arrayContaining((Object) emails.get(0))));
    }

    @Test
    public void testSendMoreThanOneEmails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/send").param("to", String.join(",", emails))
                .param("topic", "Test Topic - Multiple")
                .param("body", "Test Email Body - Multiple"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(instanceOf(List.class))))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(containsInAnyOrder(emails.get(0), emails.get(1), emails.get(2)))));

        verify(mailService).send(emailCaptor.capture(), eq("Test Topic - Multiple"), eq("Test Email Body - Multiple"));
        assertThat(emailCaptor.getValue().toArray(new String[0]), is(arrayContaining((Object) emails.get(0), emails.get(1), emails.get(2))));
    }

    @Test
    public void testSendMoreThanOneEmails_NaturalDelimiter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/send").param("to", String.join(", ", emails))
                .param("topic", "Test Topic - Multiple")
                .param("body", "Test Email Body - Multiple"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(instanceOf(List.class))))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(containsInAnyOrder(emails.get(0), emails.get(1), emails.get(2)))));

        verify(mailService).send(emailCaptor.capture(), eq("Test Topic - Multiple"), eq("Test Email Body - Multiple"));
        assertThat(emailCaptor.getValue().toArray(new String[0]), is(arrayContaining((Object) emails.get(0), emails.get(1), emails.get(2))));
    }

    @Test
    public void testSendMoreThanOneEmails_OneEmpty() throws Exception {
        emails.set(1, " ");

        mockMvc.perform(MockMvcRequestBuilders.post("/send").param("to", String.join(", ", emails))
                .param("topic", "Test Topic - Multiple")
                .param("body", "Test Email Body - Multiple"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(instanceOf(List.class))))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(containsInAnyOrder(emails.get(0), emails.get(2)))));

        verify(mailService).send(emailCaptor.capture(), eq("Test Topic - Multiple"), eq("Test Email Body - Multiple"));
        assertThat(emailCaptor.getValue().toArray(new String[0]), is(arrayContaining((Object) emails.get(0), emails.get(2))));
    }

    @Test
    public void testSendMoreThanOneEmails_DuplicateEmails() throws Exception {
        emails.set(1, emails.get(0));
        emails.set(2, emails.get(0));

        mockMvc.perform(MockMvcRequestBuilders.post("/send").param("to", String.join(",", emails))
                .param("topic", "Test Topic - Multiple")
                .param("body", "Test Email Body - Multiple"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(instanceOf(List.class))))
                .andExpect(MockMvcResultMatchers.model().attribute("to", is(containsInAnyOrder(emails.get(0)))));

        verify(mailService).send(emailCaptor.capture(), eq("Test Topic - Multiple"), eq("Test Email Body - Multiple"));
        assertThat(emailCaptor.getValue().toArray(new String[0]), is(arrayContaining((Object) emails.get(0))));
    }

    @Test
    public void testSendAndMailServiceThrowsError() throws Exception {
        doThrow(new MessagingException("Error")).when(mailService).send(anyList(), anyString(), anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/send").param("to", emails.get(0))
                .param("topic", "Test Topic")
                .param("body", "Test Email Body"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("result"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", is("Error: Error")));

        verify(mailService).send(emailCaptor.capture(), eq("Test Topic"), eq("Test Email Body"));
        assertThat(emailCaptor.getValue().toArray(new String[0]), is(arrayContaining((Object) emails.get(0))));
    }
}
