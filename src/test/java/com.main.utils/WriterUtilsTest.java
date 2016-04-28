package com.main.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.entities.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.ArrayList;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class WriterUtilsTest {

    private ArrayList<Contact> contacts;
    private BufferedWriter sbw;
    private  ReaderUtils readerUtils;

    private ObjectMapper mapper;
    private WriterUtils writer;


    @Before
    public void setup() throws Exception {
        sbw = Mockito.mock(BufferedWriter.class);
        readerUtils = Mockito.mock(ReaderUtils.class);
        contacts = new ArrayList<Contact>();
        mapper = new ObjectMapper();
        writer = new WriterUtils(sbw);

        doNothing().when(sbw).write(anyString());

        Contact contact = new Contact();
        contact.setFirstName("Joey");
        contact.setLastName("Boy");
        contact.setEmail("JBoy@mail.com");
        contacts.add(contact);

    }

    @Test
    public void writeFileWithSingleContact() throws IOException {
        when(readerUtils.read()).thenReturn(new ArrayList<Contact>());
        Contact contact = new Contact();

        writer.setReaderUtils(readerUtils);
        writer.write(contact);
        verify(sbw, (times(1))).write(mapper.writeValueAsString(contact));
        verify(sbw, (times(1))).close();
    }

    @Test
    public void writeFileWithDuplicateEmail() throws IOException {
        when(readerUtils.read()).thenReturn(contacts);

        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Boy");
        contact.setEmail("JBoy@mail.com");

        writer.setReaderUtils(readerUtils);
        writer.write(contact);

        verify(sbw, (times(1))).write(mapper.writeValueAsString(contact));
        verify(sbw, (times(1))).write(anyString());
        verify(sbw, (times(1))).close();
    }

    @After
    public void clean() {
        writer = null;
    }
}
