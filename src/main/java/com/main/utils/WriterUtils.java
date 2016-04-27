package com.main.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.entities.Contact;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WriterUtils {

    private File file = null;

    public WriterUtils(String path){
        this.file = createFile(path);
    }

    protected File createFile(String path) {
        return new File(path);
    }

    public File getFile() {
        return file;
    }

    public void write(ArrayList<Contact> contacts) throws IOException {
        contacts = removeDuplicate(contacts);
        ObjectMapper mapper = new ObjectMapper();
        List<String> lines = new ArrayList<String>();
        for( Contact contact : contacts ){
            String stringContact = mapper.writeValueAsString(contact);
            lines.add(stringContact);
        }

        Files.write(Paths.get(file.getPath()), lines, Charset.forName("UTF-8"));
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
