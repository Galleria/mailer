package com.main.utils;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class WriterUtils {

    private File file = null;


    public WriterUtils(String path){
        file = new File(path);
    }

    public File getFile() {
        return file;
    }

    public void write(String message) throws IOException {
        List<String> lines = Arrays.asList(message);
        Files.write(Paths.get(file.getPath()), lines, Charset.forName("UTF-8"));
    }
}
