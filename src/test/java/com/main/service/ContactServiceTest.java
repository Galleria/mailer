package com.main.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;

public class ContactServiceTest {

    private ContactService contactService;
    private ContactService spyContactService;
    private File file;

    @Test
    public void initialFile() throws IOException {
        assertNotNull(contactService.initialFile());
    }

    @Before
    public void setup() {
        contactService = new ContactService();
        spyContactService = spy(contactService);
        file = Mockito.mock(File.class);
        contactService.setFile(file);
    }
}
