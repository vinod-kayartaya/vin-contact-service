package com.kvinod.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
public class ContactList {
    @XmlElement(name="contacts")
    private ArrayList<Contact> contacts = new ArrayList<>();

    public ContactList(List<Contact> contacts){
        this.contacts.addAll(contacts);
    }
}
