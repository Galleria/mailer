package com.main.utils;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReaderUtilsTest {

    private ReaderUtils reader;
    private String expect = "Expected message";
    private String path = "testFile.txt";

    @Before
    public void setup(){
        reader = new ReaderUtils( path );
    }

    @Test
    public void read() throws IOException {
        if( write() ){
            assertEquals(expect, reader.read());
        }
    }

    private boolean write(){
        File file = new File(path);
        List<String> lines = Arrays.asList(expect);
        try {
            Files.write(Paths.get(file.getPath()), lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @After
    public void clean(){
        reader.getFile().delete();
        reader = null;
    }

}
