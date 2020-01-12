/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Role;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

/**
 *
 * @author benja
 */
public class PersonFacadeTest {
    
    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    private Person personOne;
    private Person personTwo;
    private Person personThree;
    private Hobby hobbyOne;
    private Hobby hobbyTwo;
    private Address addressOne;
    private Address addressTwo;
   
            
    
    public PersonFacadeTest() {
    }
    
       @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = PersonFacade.getPersonFacade(emf);
    }
    
 

   @BeforeEach
    public void setUp() {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            //Delete existing people to get a "fresh" database


            personOne = new Person("testName", "testLastName", "test@mail.com", 21212121);
            personTwo = new Person("testName2", "testLastName2", "test@mail.com2", 98989898);
            personThree = new Person("testName3", "testLastName3", "test@mail.com3", 19191919);
            List<Person> people =  new ArrayList<Person>();
            List<Person> people2 =  new ArrayList<Person>();
            people.add(personOne);
            people.add(personTwo);
            people2.add(personThree);
            hobbyOne = new Hobby("testHobbyName", "testDescription");
            hobbyTwo = new Hobby("testHobbyName2", "testDescription2");
            addressOne = new Address("testStreet1", 2323 );
            addressTwo = new Address("testStreet2", 2929 );
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
            
          
            System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    


 

    /**
     * Test of getPersonByName method, of class PersonFacade.
     */
    @Test
    public void testGetPersonByName() {
        System.out.println("testGetPersonByName");
        String name = "testName";
        facade = PersonFacade.getPersonFacade(emf);
        String expResult = "testLastName";
        String result = facade.getPersonByName(name).getLastName();
        assertEquals(expResult, result);
     
    }
    
        /**
     * Test of getPersonByName method, of class PersonFacade.
     */
    @Test
    public void testGetPersonByEmail() {
        System.out.println("testGetPersonByEmail");
        String email = "test@mail.com";
        facade = PersonFacade.getPersonFacade(emf);
        String expResult = "testLastName";
        String result = facade.getPersonByEmail(email).getLastName();
        assertEquals(expResult, result);
     
    }
    
           /**
     * Test of getPersonByName method, of class PersonFacade.
     */
    @Test
    public void testGetPersonById() {
        System.out.println("testGetPersonById");
        Long id = personOne.getId();
        facade = PersonFacade.getPersonFacade(emf);
        String expResult = "testLastName";
        String result = facade.getPersonById(id).getLastName();
        assertEquals(expResult, result);
     
    }
    
              /**
     * Test of getPersonByName method, of class PersonFacade.
     */
    @Test
    public void testGetPersonByPhone() {
        System.out.println("testGetPersonByPhone");
        int phone = personOne.getPhone();
        facade = PersonFacade.getPersonFacade(emf);
        String expResult = "testLastName";
        String result = facade.getPersonByPhone(phone).getLastName();
        assertEquals(expResult, result);
     
    }
    
               /**
     * Test of getPersonByName method, of class PersonFacade.
     */
    @Test
    public void testGetPeopleByHobby() {
        System.out.println("testGetPeopleByHobby");
        String hobby = "testHobbyName";
        facade = PersonFacade.getPersonFacade(emf);

        int result = facade.getPersonByHobby(hobby).size();
        assertTrue(result > 0);
     
    }

    
                  /**
     * Test of getPersonByName method, of class PersonFacade.
     */
    @Test
    public void testGetAllHobbies() {
        System.out.println("testGetAllHobbies");
       
        facade = PersonFacade.getPersonFacade(emf);

        int result = facade.getAllHobby().size();
        assertTrue(result>0);
     
    }
    
     @Test
    public void testDeleteHobby() {
        System.out.println("testDeleteHobby");
        String hobby = "testHobbyName";
        facade = PersonFacade.getPersonFacade(emf);

        int before = facade.getAllHobby().size();
        facade.deletehobby(hobby);
        int after = facade.getAllHobby().size();
        assertTrue(before > after);
     
    }
    
      @Test
    public void testAddHobby() {
        System.out.println("testAddHobby");
       
        facade = PersonFacade.getPersonFacade(emf);
        String name = "newHobby";
        String description = "newHobbyDescription";
        int before = facade.getAllHobby().size();
        facade.addHobby(name, description);
        int after = facade.getAllHobby().size();
        assertTrue(before < after);
     
    }
    
         @Test
    public void testEditHobby() {
        System.out.println("testAddHobby");
       
        facade = PersonFacade.getPersonFacade(emf);
        String name = "editedHobbyname";
        String description = "editedHobbyDescription";
        String before = hobbyTwo.getName();
        Long id = hobbyTwo.getId();
        
        facade.editHobby(id, name, description);
        String after = facade.getHobbyById(id).getName();
        assertEquals(name, after);
     
    }
    
          @Test
    public void testAddPerson() {
        System.out.println("testAddPerson");
       
        facade = PersonFacade.getPersonFacade(emf);
        facade.addPerson("newPerson", "NewLastName", "new@email.com", 2112);
        String before = facade.getPersonByName("newPerson").getFirstName();
              assertEquals(before, "newPerson");
     
    }
    
        
     @Test
    public void testDeletePerson() {
        System.out.println("testDeletePerson");
        facade = PersonFacade.getPersonFacade(emf);
        int before = facade.getPeople().size();
        facade.deletePerson(personThree.getFirstName());
        int after = facade.getPeople().size();
        assertTrue(before - 1 == after);
     
    }
    
       @Test
    public void testEditPerson() {
        System.out.println("testEditPerson");
        facade = PersonFacade.getPersonFacade(emf);
        Person personbefore = facade.getPersonById(personOne.getId());
       
        facade.editPerson(personbefore.getId(), "EditedName", "EditedLastName", "Edit@email.com", 3232, personTwo.getHobbyes() , addressTwo);
        Person personAfter = facade.getPersonById(personOne.getId());
       
        assertTrue(!personbefore.getEmail().equals(personAfter.getEmail()));
     
    }
    
}
