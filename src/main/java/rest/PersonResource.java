/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Address;
import entities.Hobby;
import entities.Person;
import errorhandling.NotFoundException;
import facades.PersonFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author aamandajuhl
 */
@Path("people")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final PersonFacade PF = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPeople() throws NotFoundException {
        List<Person> people = PF.getPeople();
        return GSON.toJson(people);
    }

    @Path("populate")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String podulate() throws NotFoundException {

        Person personOne;
        Person personTwo;
        Person personThree;
        Hobby hobbyOne;
        Hobby hobbyTwo;
        Address addressOne;
        Address addressTwo;
        EntityManager em = EMF.createEntityManager();

        try {
            em.getTransaction().begin();
            //Delete existing people to get a "fresh" database

            personOne = new Person("Joe", "Joeson", "joe@mail.com", 21212121);
            personTwo = new Person("albert", "albertsen", "albert@mail.com2", 98989898);
            personThree = new Person("andrea", "jensen", "andrea@mail.com3", 19191919);
            List<Person> people = new ArrayList<Person>();
            List<Person> people2 = new ArrayList<Person>();
            people.add(personOne);
            people.add(personTwo);
            people2.add(personThree);
            hobbyOne = new Hobby("knitting", "like eating nudels but more productive");
            hobbyTwo = new Hobby("MMA", "war on a smaller scale");
            addressOne = new Address("guldbergsgade", 2200);
            addressTwo = new Address("rosenvej", 2920);
            addressOne.setPeople(people);
            addressTwo.setPeople(people2);
            personOne.setAddress(addressOne);
            personTwo.setAddress(addressOne);
            personThree.setAddress(addressTwo);

            personOne.addHobby(hobbyOne);
            personTwo.addHobby(hobbyOne);
            personThree.addHobby(hobbyTwo);
            hobbyOne.addPerson(personOne);
            hobbyTwo.addPerson(personThree);

            em.persist(personOne);
            em.persist(personThree);

            System.out.println("Saved data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return GSON.toJson("Podulation compleat!");
    }
}
