package com.main.controller;

import com.main.entities.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cadet on 4/28/2016 AD.
 */
@Controller
public class ContactController {

    @RequestMapping(value = "/addcontact", method = RequestMethod.POST)
    @ResponseBody
    Contact addContact(Contact contact){
        System.out.println( "in addContact" );
        return contact;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    @ResponseBody
    List<Contact> getContacts(){
        System.out.println( "in getContacts" );
        List<Contact> contacts = stubContacts(5);

        return contacts;
    }

    private List<Contact> stubContacts(int amount){
        List<Contact> contacts = new ArrayList<Contact>();
        for (int i = 0; i < amount; i++) {
            Contact contact = new Contact("F"+String.valueOf(i), "L"+String.valueOf(i), "e"+String.valueOf(i));
            contacts.add(contact);
        }
        return contacts;
    }
}
