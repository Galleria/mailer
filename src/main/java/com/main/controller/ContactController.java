package com.main.controller;

import com.main.entities.Contact;
import com.main.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ContactController {

    @Autowired
    ContactService contactService;

    @RequestMapping(value = "/addcontact", method = RequestMethod.POST)
    @ResponseBody
    void addContact(Contact contact) throws IOException {
        File file = contactService.initialFile();
        contactService.write(file,contact);
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    @ResponseBody
    List<Contact> getContacts() throws IOException {
        File file = contactService.initialFile();
        List<Contact> contacts = contactService.read(file);
        return contacts;
    }

    @RequestMapping(value = "/contacts/clear", method = RequestMethod.GET)
    @ResponseBody
    void clearContacts() throws IOException {
        contactService.moveContentToTemp();
    }

    @RequestMapping(value = "/contacts/restore", method = RequestMethod.GET)
    @ResponseBody
    void restoreContacts() throws IOException {
        contactService.restoreContentFromTemp();
    }
}
