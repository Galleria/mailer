package com.main.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.entities.Contact;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriterUtils {

    private BufferedWriter bw = null;
    private ReaderUtils readerUtils = null;

    public WriterUtils( BufferedWriter bw ) throws IOException {
        this.bw = bw;
    }

    public void setReaderUtils(ReaderUtils readerUtils) {
        this.readerUtils = readerUtils;
    }

    public void write(Contact contact) throws IOException {
        ArrayList<Contact> contacts = getRead();
        contacts.add(contact);
        contacts = removeDuplicate(contacts);
        ObjectMapper mapper = new ObjectMapper();

        for( Contact tempContact : contacts ){
            bw.write(mapper.writeValueAsString(tempContact));
        }

        bw.close();
    }

    protected ArrayList<Contact> getRead() throws IOException {
        return this.readerUtils.read();
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
