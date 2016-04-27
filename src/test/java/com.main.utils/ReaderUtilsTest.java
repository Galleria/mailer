package com.main.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.entities.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReaderUtilsTest {

    private ReaderUtils reader;
    private ArrayList<Contact> expectedContacts;
    //FIXME We shouldn't communicate with system
    private String path = "testFile.json";

    @Before
    public void setup(){
        reader = new ReaderUtils( path );
        expectedContacts = new ArrayList<Contact>();

        Contact contact = new Contact();
        contact.setFirstName("Joey");
        contact.setLastName("Boy");
        contact.setEmail("JBoy@mail.com");
        expectedContacts.add(contact);
    }

    @Test
    public void readOneLine() throws IOException {
        if( write(expectedContacts) ){
            ArrayList<Contact> resultContacts = reader.read();
            assertContactsEqual(resultContacts);
        }
    }

    @Test
    public void readMoreThanOneLine() throws IOException {
        Contact contact = new Contact();
        contact.setFirstName("Kim");
        contact.setLastName("Berry");
        contact.setEmail("KBerry@mail.com");
        expectedContacts.add(contact);

        if( write(expectedContacts) ){
            ArrayList<Contact> resultContacts = reader.read();
            assertContactsEqual(resultContacts);
        }
    }

    private void assertContactsEqual(ArrayList<Contact> resultContacts) {
        assertEquals(expectedContacts.size(), resultContacts.size());
        for ( int i = 0; i < resultContacts.size(); i++ ) {
            assertEquals(expectedContacts.get(i).getFirstName(), resultContacts.get(i).getFirstName());
            assertEquals(expectedContacts.get(i).getLastName(), resultContacts.get(i).getLastName());
            assertEquals(expectedContacts.get(i).getEmail(), resultContacts.get(i).getEmail());
        }
    }

    private boolean write(ArrayList<Contact> contacts) {
        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();
        List<String> lines = new ArrayList<String>();

        try {
            for( Contact contact : contacts ){
                String stringContact = mapper.writeValueAsString(contact);
                lines.add(stringContact);
            }
            Files.write(Paths.get(file.getPath()), lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @After
    public void clean(){
        reader.getFile().delete();
        reader = null;
    }

}
