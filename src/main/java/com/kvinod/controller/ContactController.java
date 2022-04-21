package com.kvinod.controller;

import com.kvinod.model.Contact;
import com.kvinod.model.ContactList;
import com.kvinod.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService service;

    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getAllContacts(
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(required = false, defaultValue = "") String phone,
            @RequestParam(required = false, defaultValue = "") String city,
            @RequestParam(required = false, defaultValue = "") String state,
            @RequestParam(required = false, defaultValue = "") String country,
            @RequestParam(required = false, defaultValue = "") String gender
    ) {
        if (!city.equals("")) {
            return ResponseEntity.ok(new ContactList(service.getContactsByCity(city)));
        }
        if (!state.equals("")) {
            return ResponseEntity.ok(new ContactList(service.getContactsByState(state)));
        }
        if (!country.equals("")) {
            return ResponseEntity.ok(new ContactList(service.getContactsByCountry(country)));
        }
        if (!gender.equals("")) {
            return ResponseEntity.ok(new ContactList(service.getContactsByGender(gender)));
        }
        if (!email.equals("")) {
            Contact c = service.getContactsByEmail(email);
            if(c==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found for email " +email);
            }
            return ResponseEntity.ok(c);
        }
        if (!phone.equals("")) {
            Contact c = service.getContactsByPhone(phone);
            if(c==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found for phone " +phone);
            }
            return ResponseEntity.ok(c);
        }

        return ResponseEntity.ok(new ContactList(service.getAllContacts()));
    }

    @GetMapping(path = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getById(@PathVariable String id) {
        Contact c = service.getContactById(id);
        if(c==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found for id - " + id);
        }
        return ResponseEntity.ok(c);
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> addNewContact(@RequestBody Contact contact) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addNewContact(contact));
    }


    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> addNewContactFromFormData(Contact contact) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addNewContact(contact));
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Contact updateContact(@PathVariable String id, @RequestBody Contact contact) {
        contact.setId(id);
        return service.updateContact(contact);
    }

    @DeleteMapping(path = "/{id}", produces = {"application/json", "application/xml"})
    public Contact deleteContact(@PathVariable String id) {
        return service.deleteContact(id);
    }
}
