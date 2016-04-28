package com.main.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.entities.Contact;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriterUtils {

    private BufferedWriter bw = null;
    private ArrayList<Contact> contacts = null;

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public WriterUtils( BufferedWriter bw ) throws IOException {
        this.bw = bw;
    }

    public void write(Contact contact) throws IOException {
        contacts.add(contact);
        contacts = removeDuplicate(contacts);
        ObjectMapper mapper = new ObjectMapper();

        for( Contact tempContact : contacts ){
            bw.write(mapper.writeValueAsString(tempContact));
            bw.write(System.lineSeparator());
        }
        bw.flush();
        bw.close();
    }

    private ArrayList<Contact> removeDuplicate(ArrayList<Contact> contacts){
        for( int i=0;i< contacts.size();i++ ){
            String email = contacts.get(i).getEmail();
            for(int j=i+1;j<contacts.size();j++){
                if( email.equals(contacts.get(j).getEmail())){
                    contacts.remove(i);
                }
            }
        }
        return contacts;
    }
}
