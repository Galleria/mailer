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
        if(!this.file.exists()){
            this.file.createNewFile();
        }
        return this.file;
    }

    public List<Contact> read(File file) throws IOException {
       ReaderUtils reader = getReaderUtils(file);
       return reader.read();
    }

    public void write(File file,Contact contact) throws IOException {
        ArrayList<Contact> contacts = (ArrayList<Contact>) this.read(file);
        WriterUtils writer = getWriterUtils(file);
        writer.setContacts(contacts);
        writer.write(contact);
    }

    protected ReaderUtils getReaderUtils(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
        return new ReaderUtils(bufferedReader);
    }

    protected WriterUtils getWriterUtils(File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        return new WriterUtils(bufferedWriter);
    }

    public void moveContentToTemp() throws IOException{
        swapContentBetweenTwoFiles("temp" + Constant.PATH, Constant.PATH);

        File file = new File(Constant.PATH);
        PrintWriter writer = new PrintWriter(file);
        writer.print("");

        writer.close();
    }

    private void swapContentBetweenTwoFiles(String destination, String source) throws IOException {
        File tempFile = new File(destination);
        if(!tempFile.exists()){
            tempFile.createNewFile();
        }

        File sourceFile = new File(source);
        if(sourceFile.exists()){
            BufferedWriter bw = getBufferedWriter(tempFile);
            BufferedReader br = getBufferedReader(source);

            String line;
            while((line = br.readLine()) != null) {
                bw.write(line);
                bw.write(System.lineSeparator());
            }
            bw.flush();
            bw.close();
        }

    }

    private BufferedWriter getBufferedWriter(File tempFile) throws IOException {
        FileWriter fw = new FileWriter(tempFile.getAbsoluteFile());
        return new BufferedWriter(fw);
    }

    private BufferedReader getBufferedReader(String source) throws FileNotFoundException {
        File file = new File(source);
        return new BufferedReader(new FileReader(file.getAbsoluteFile()));
    }

    public void restoreContentFromTemp()throws IOException{
        swapContentBetweenTwoFiles(Constant.PATH, "temp" + Constant.PATH);

        File tempFile = new File("temp" + Constant.PATH);
        tempFile.delete();
    }
}
