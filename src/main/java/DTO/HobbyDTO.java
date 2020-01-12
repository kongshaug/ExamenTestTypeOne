/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benja
 */
public class HobbyDTO {
    
     private static final long serialVersionUID = 1L;


    private String name;
    private String description;
   
    private List<String> people = new ArrayList<String>();


    public HobbyDTO(Hobby hobby) {
        this.name = hobby.getName();
        this.description = hobby.getDescription();
        for (Person p : hobby.getPeople()) {
            people.add(p.getFirstName());
        }
    }
    
}
