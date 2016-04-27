package com.main.utils;

import com.main.entities.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class WriterUtilsTest {

    private WriterUtils writer;
    private ArrayList<Contact> contacts;
    private String expected;
    //FIXME We shouldn't communicate with system
    private String path = "testFile.json";

    @Before
    public void setup() {
        writer = new WriterUtils(path);
        contacts = new ArrayList<Contact>();

        Contact contact = new Contact();
        contact.setFirstname("Joey");
        contact.setLastname("Boy");
        contact.setEmail("JBoy@mail.com");
        contacts.add(contact);

        expected = "{\"firstname\":\"Joey\",\"lastname\":\"Boy\",\"email\":\"JBoy@mail.com\"}";
    }

    @Test
    public void writeFileWithSingleContact() throws IOException {
        writer.write( contacts );
        assertEquals(expected, read(writer.getFile()));
    }

    @Test
    public void writeFileWithMoreContacts() throws IOException {
        Contact contact = new Contact();
        contact.setFirstname("Kim");
        contact.setLastname("Berry");
        contact.setEmail("KBerry@mail.com");
        contacts.add(contact);

        expected += "{\"firstname\":\"Kim\",\"lastname\":\"Berry\",\"email\":\"KBerry@mail.com\"}";

        writer.write( contacts );
        assertEquals(expected, read(writer.getFile()));
    }

    @Test
    public void writeFileWithDuplicateEmail() throws IOException {
        Contact contact = new Contact();
        contact.setFirstname("John");
        contact.setLastname("Boy");
        contact.setEmail("JBoy@mail.com");
        contacts.add(contact);

        expected = "{\"firstname\":\"John\",\"lastname\":\"Boy\",\"email\":\"JBoy@mail.com\"}";

        writer.write(contacts);
        assertEquals(expected, read(writer.getFile()));
    }

    private String read(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
        String result = "";
        String line;
        while((line = br.readLine()) != null) {
            result += line;
        }
        return result;
    }

    @After
    public void clean() {
        writer.getFile().delete();
        writer = null;
    }
}
