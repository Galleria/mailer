package com.main.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.entities.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ReaderUtilsTest {

    private ReaderUtils reader;
    private ArrayList<Contact> expectedContacts;
    private Contact contact;
    private BufferedReader bufferedReader;
    private ObjectMapper mapper;

    @Before
    public void setup() throws IOException {
        bufferedReader = Mockito.mock(BufferedReader.class);
        reader = new ReaderUtils(bufferedReader);
        expectedContacts = new ArrayList<>();

        contact = new Contact();
        contact.setFirstName("Joey");
        contact.setLastName("Boy");
        contact.setEmail("JBoy@mail.com");
        expectedContacts.add(contact);

        mapper = new ObjectMapper();
    }

    @Test
    public void readOneLine() throws IOException {
        String line = mapper.writeValueAsString(contact);
        when(bufferedReader.readLine()).thenReturn(line).thenReturn(null);

        ArrayList<Contact> resultContacts = reader.read();
        assertContactsEqual(resultContacts);
        verify(bufferedReader,times(2)).readLine();
    }

    @Test
    public void readMoreThanOneLine() throws IOException {
        Contact contact2 = new Contact();
        contact2.setFirstName("Kim");
        contact2.setLastName("Berry");
        contact2.setEmail("KBerry@mail.com");
        expectedContacts.add(contact2);

        String line = mapper.writeValueAsString(contact);
        String line2 = mapper.writeValueAsString(contact2);
        when(bufferedReader.readLine()).thenReturn(line).thenReturn(line2).thenReturn(null);

        ArrayList<Contact> resultContacts = reader.read();
        assertContactsEqual(resultContacts);
        verify(bufferedReader,times(3)).readLine();
    }

    private void assertContactsEqual(ArrayList<Contact> resultContacts) {
        assertEquals(expectedContacts.size(), resultContacts.size());
        for ( int i = 0; i < resultContacts.size(); i++ ) {
            assertEquals(expectedContacts.get(i).getFirstName(), resultContacts.get(i).getFirstName());
            assertEquals(expectedContacts.get(i).getLastName(), resultContacts.get(i).getLastName());
            assertEquals(expectedContacts.get(i).getEmail(), resultContacts.get(i).getEmail());
        }
    }

    @After
    public void clean(){
        reader = null;
    }

}
