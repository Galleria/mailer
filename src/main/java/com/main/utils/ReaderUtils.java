package com.main.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.entities.Contact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReaderUtils {
    private File file = null;

    public ReaderUtils(String path) {
        file = new File(path);
    }

    public File getFile() {
        return file;
    }

    public ArrayList<Contact> read() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        BufferedReader br = new BufferedReader(new FileReader(file.getPath()));

        String line;
        while((line = br.readLine()) != null) {
            Contact contact = mapper.readValue(line,Contact.class);
            contacts.add(contact);
        }
        return contacts;
    }

}
