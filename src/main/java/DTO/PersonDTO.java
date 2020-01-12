/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Person;
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
//    private List<Hobby> hobbyes = new ArrayList<Hobby>();
//    private Address address;

    public PersonDTO(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        this.phone = person.getPhone();
    }
    
    
    
}
