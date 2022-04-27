package com.kvinod.controller;

import com.kvinod.model.Contact;
import com.kvinod.model.ContactList;
import com.kvinod.model.ErrorInfo;
import com.kvinod.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
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
            @RequestParam(required = false, defaultValue = "") String gender,
            HttpServletResponse response
    ) {

        Cookie cookie1 = new Cookie("Created-By", "Vinod");
        Cookie cookie2 = new Cookie("Powered-By", "Springboot");
        response.addCookie(cookie1);
        response.addCookie(cookie2);

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
                ErrorInfo ei = new ErrorInfo("No data found for email " +email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ei);
            }
            return ResponseEntity.ok(c);
        }
        if (!phone.equals("")) {
            Contact c = service.getContactsByPhone(phone);
            if(c==null){
                ErrorInfo ei = new ErrorInfo("No data found for phone " +phone);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ei);
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


    @GetMapping(path = "/{id}", produces = {"text/plain"})
    public ResponseEntity<Object> getByIdAsText(@PathVariable String id) {
        Contact c = service.getContactById(id);
        if(c==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found for id - " + id);
        }
        return ResponseEntity.ok(c.toString());
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


    @PatchMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Contact patchContact(@PathVariable String id, @RequestBody Contact contact) {
        Contact oldContact = service.getContactById(id);
        contact.setId(id);

        log.info("contact is {}", contact);

        if(contact.getFirstname()==null){
            contact.setFirstname(oldContact.getFirstname());
        }
        if(contact.getLastname()==null){
            contact.setLastname(oldContact.getLastname());
        }
        if(contact.getEmail()==null){
            contact.setEmail(oldContact.getEmail());
        }
        if(contact.getPhone()==null){
            contact.setPhone(oldContact.getPhone());
        }
        if(contact.getGender()==null){
            contact.setGender(oldContact.getGender());
        }
        if(contact.getAddress()==null){
            contact.setAddress(oldContact.getAddress());
        }
        if(contact.getCity()==null){
            contact.setCity(oldContact.getCity());
        }
        if(contact.getState()==null){
            contact.setState(oldContact.getState());
        }
        if(contact.getCountry()==null){
            contact.setCountry(oldContact.getCountry());
        }
        if(contact.getPincode()==null){
            contact.setPincode(oldContact.getPincode());
        }
        return service.updateContact(contact);
    }

    @DeleteMapping(path = "/{id}", produces = {"application/json", "application/xml"})
    public Contact deleteContact(@PathVariable String id) {
        return service.deleteContact(id);
    }
}
