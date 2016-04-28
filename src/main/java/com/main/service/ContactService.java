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

    private File file = new File(Constant.PATH);

    public void setFile(File file) {
        this.file = file;
    }

    public File initialFile() throws IOException {
        if(!file.exists()){
            file.createNewFile();
        }
        return file;
    }

   public List<Contact> read(File file) throws IOException {
       ReaderUtils reader = getReaderUtils(file);
       return reader.read();
    }

    protected ReaderUtils getReaderUtils(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
        return new ReaderUtils(bufferedReader);
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
