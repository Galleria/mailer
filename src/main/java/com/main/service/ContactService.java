package com.main.service;

import com.main.entities.Contact;
import com.main.utils.Constant;
import com.main.utils.ReaderUtils;
import com.main.utils.WriterUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    public File initialFile() throws IOException {
        File file = new File(Constant.PATH);
        if(!file.exists()){
            file.createNewFile();
        }
        return file;
    }

   public List<Contact> read(File file) throws IOException {
       BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
       ReaderUtils reader = new ReaderUtils(bufferedReader);
       return reader.read();
    }


    public void write(File file,Contact contact) throws IOException {
        ArrayList<Contact> contacts = (ArrayList<Contact>) this.read(file);

        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        WriterUtils writer = new WriterUtils(bufferedWriter);

        writer.setContacts(contacts);
        writer.write(contact);
    }
}
