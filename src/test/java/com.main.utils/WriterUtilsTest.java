package com.main.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class WriterUtilsTest {

    WriterUtils writer;
    private String path = "testFile.txt";

    @Before
    public void setup() {
        writer = new WriterUtils(path);
    }

    @Test
    public void writeFile() throws IOException {
        String expected = "Expected message";
        writer.write(expected);

        assertEquals(expected, read(writer.getFile()));

    }

    private String read(File file) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
        String result = "";
        String line;
        while((line = br.readLine()) != null) {
            result += line;
        }
        return result;
    }

    @After
    public void clean() {
        writer.getFile().delete();
    }
}
