/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author benja
 */
public class PersonDTO {
    
 
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private ArrayList<HobbyDTO> hobbies = new ArrayList<HobbyDTO>();
    private String street;
    private int zip;

    
        public PersonDTO(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        this.phone = person.getPhone();
        for (Hobby hobby : person.getHobbyes()) {
            hobbies.add(new HobbyDTO(hobby));
        }
        this.street = person.getAddress().getStreet();
        this.zip = person.getAddress().getZip();
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }


    
    
    
}
