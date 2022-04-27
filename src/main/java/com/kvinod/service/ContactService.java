package com.kvinod.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kvinod.model.Contact;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ContactService {
    private Map<String, Contact> contactList = new LinkedHashMap<>();

    public ContactService() throws IOException {
        ObjectMapper om = new ObjectMapper();
        List<Contact> contacts = Arrays.asList(om.readValue(Paths.get("contacts.json").toFile(), Contact[].class));
        contactList = contacts.stream()
                .collect(Collectors.toMap(Contact::getId, Function.identity()));
    }

    public Contact addNewContact(Contact contact) {
        if(contact.getFirstname()==null || contact.getFirstname().trim().length()==0){
            throw new ServiceException("Firstname is required but missing");
        }
        if(contact.getEmail()==null || contact.getEmail().trim().length()==0){
            throw new ServiceException("Email is required but missing");
        }
        if(contact.getPhone()==null || contact.getPhone().trim().length()==0){
            throw new ServiceException("Phone number is required but missing");
        }
        if (contactList
                .values()
                .stream()
                .filter(c -> c.getEmail() != null)
                .filter(c -> c.getEmail().equalsIgnoreCase(contact.getEmail()))
                .findFirst()
                .isPresent()) {
            throw new ServiceException("Contact with this email already present");
        }
        if (contactList
                .values()
                .stream()
                .filter(c -> c.getPhone() != null)
                .filter(c -> c.getPhone().equalsIgnoreCase(contact.getPhone()))
                .findFirst()
                .isPresent()) {
            throw new ServiceException("Contact with this phone already present");
        }
        contact.setId(UUID.randomUUID().toString());
        contactList.put(contact.getId(), contact);
        return contact;
    }

    public Contact getContactById(String id) {
        return contactList.get(id);
    }

    public Contact updateContact(Contact contact) {
        if (!contactList.containsKey(contact.getId())) {
            throw new ServiceException("Contact with this id does not exist - " + contact.getId());
        }
        contactList.put(contact.getId(), contact);
        return contact;
    }

    public Contact deleteContact(String id) {
        if (!contactList.containsKey(id)) {
            throw new ServiceException("Contact with this id does not exist - " + id);
        }
        return contactList.remove(id);
    }

    public List<Contact> getAllContacts() {
        return contactList.values().stream().collect(Collectors.toList());
    }

    public List<Contact> getContactsByGender(String gender) {
        return contactList
                .values()
                .stream()
                .filter(c -> c.getGender() != null)
                .filter(c -> c.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }

    public List<Contact> getContactsByCity(String city) {
        return contactList
                .values()
                .stream()
                .filter(c -> c.getCity() != null)
                .filter(c -> c.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    public List<Contact> getContactsByState(String state) {
        return contactList
                .values()
                .stream()
                .filter(c -> c.getState() != null)
                .filter(c -> c.getState().equalsIgnoreCase(state))
                .collect(Collectors.toList());
    }

    public List<Contact> getContactsByCountry(String country) {
        return contactList
                .values()
                .stream()
                .filter(c -> c.getCountry() != null)
                .filter(c -> c.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());
    }

    public Contact getContactsByEmail(String email) {
        return contactList
                .values()
                .stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public Contact getContactsByPhone(String phone) {
        return contactList
                .values()
                .stream()
                .filter(c -> c.getPhone().equalsIgnoreCase(phone))
                .findFirst()
                .orElse(null);
    }
}
