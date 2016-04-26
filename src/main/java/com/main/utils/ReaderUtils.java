package com.main.utils;


import java.io.*;

public class ReaderUtils {
    private File file = null;

    public ReaderUtils(String path) {
        file = new File(path);
    }

    public File getFile() {
        return file;
    }

    public String read() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
        String result = "";
        String line;
        while((line = br.readLine()) != null) {
            result += line;
        }
        return result;
    }

}
