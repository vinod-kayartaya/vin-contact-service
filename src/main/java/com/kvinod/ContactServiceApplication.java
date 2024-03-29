package com.kvinod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ContactServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactServiceApplication.class, args);
	}

	@GetMapping("/")
	public String index(){
		return "Hello, and welcome.<br />" +
				"<br />" +
				"You can access the contact service using the following link:<br />" +
				"<br />" +
				"<a href='https://vin-contact-service.herokuapp.com/api/contacts'>https://vin-contact-service.herokuapp.com/api/contacts</a><br />" +
				"<br />" +
				"You may also ask for either JSON or XML content.";
	}

}
