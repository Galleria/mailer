package com.main.service;

import com.main.entities.Contact;
import com.main.utils.ReaderUtils;
import com.main.utils.WriterUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ContactServiceTest extends ContactService{

    private ContactServiceTest contactService;
    private File file;
    private WriterUtils writer;

    @Override
    protected ReaderUtils getReaderUtils(File file) throws IOException {
        ReaderUtils reader = Mockito.mock(ReaderUtils.class);
        when(reader.read()).thenReturn(new ArrayList<>());
        return reader;
    }

    @Override
    protected WriterUtils getWriterUtils(File file) throws IOException {
        return writer;
    }

    @Before
    public void setup() {
        contactService = new ContactServiceTest();
        file = Mockito.mock(File.class);
        contactService.setFile(file);
        writer = Mockito.mock(WriterUtils.class);
    }

    @Test
    public void initialFileWithMock() throws IOException {
        when(file.exists()).thenReturn(false);
        contactService.setFile(file);
        assertNotNull(contactService.initialFile());
    }

    @Test
    public void readContacts() throws IOException {
        ArrayList<Contact> resultContacts = (ArrayList<Contact>) contactService.read(file);
        assertTrue(resultContacts.isEmpty());
    }

    @Test
    public void writeContact() throws IOException {
        write(file,new Contact());
        assertWriter();
    }

    private void assertWriter() throws IOException {
        verify(writer,times(1)).setContacts(any(ArrayList.class));
        verify(writer,times(1)).write(any(Contact.class));
    }

}
