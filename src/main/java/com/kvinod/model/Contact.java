package com.kvinod.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Contact {
    private String id;
    private String firstname;
    private String lastname;
    private String gender;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
