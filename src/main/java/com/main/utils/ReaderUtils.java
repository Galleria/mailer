package com.main.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.entities.Contact;

import java.io.*;
import java.util.ArrayList;

public class ReaderUtils {
    private BufferedReader bufferedReader = null;

    public ReaderUtils(BufferedReader bufferedReader) throws IOException {
        this.bufferedReader = bufferedReader;
    }

    public ArrayList<Contact> read() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        String line;
        while((line = bufferedReader.readLine()) != null) {
            Contact contact = mapper.readValue(line,Contact.class);
            contacts.add(contact);
        }
        bufferedReader.close();
        return contacts;
    }
}
