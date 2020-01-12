/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.User;
import errorhandling.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author benja
 */
public class PersonFacade {

    private static EntityManagerFactory emf;
    private static PersonFacade instance;

    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

        public List<Person> getPeople() {
        EntityManager em = emf.createEntityManager();
      List<Person> people;
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            people = query.getResultList();
            //make catch if user == null
        } finally {
            em.close();
        }
        return people;
    }
    
    public Person getPersonByName(String name) {
        EntityManager em = emf.createEntityManager();
        Person person;
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.firstName = :firstName", Person.class);
            person = query.setParameter("firstName", name).getResultList().get(0);
            //make catch if user == null
        } finally {
            em.close();
        }
        return person;
    }

    public Person getPersonByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        Person person;
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.email = :email", Person.class);
            person = query.setParameter("email", email).getResultList().get(0);
            //make catch if user == null
        } finally {
            em.close();
        }
        return person;
    }

    public Person getPersonById(Long id) {
        EntityManager em = emf.createEntityManager();
        Person person;
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.id = :id", Person.class);
            person = query.setParameter("id", id).getResultList().get(0);
            //make catch if user == null
        } finally {
            em.close();
        }
        return person;
    }

    public Person getPersonByPhone(int phone) {
        EntityManager em = emf.createEntityManager();
        Person person;
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.phone = :phone", Person.class);
            person = query.setParameter("phone", phone).getResultList().get(0);
            //make catch if user == null
        } finally {
            em.close();
        }
        return person;
    }

    public List<Person> getPersonByHobby(String hobby) {
        EntityManager em = emf.createEntityManager();
        List<Person> people;
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p INNER JOIN p.hobbyes h WHERE h.name = :hobby", Person.class);

            people = query.setParameter("hobby", hobby).getResultList();
            //make catch if user == null
        } finally {
            em.close();
        }
        return people;
    }

    public List<Hobby> getAllHobby() {
        EntityManager em = emf.createEntityManager();
        List<Hobby> hobbies;
        try {
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h", Hobby.class);

            hobbies = query.getResultList();
            //make catch if user == null
        } finally {
            em.close();
        }
        return hobbies;
    }

    
    public void deletehobby(String hobby) {
        EntityManager em = emf.createEntityManager();
        List<Person> people;
        try {
              em.getTransaction().begin();
            TypedQuery query = em.createQuery("Delete FROM Hobby h WHERE h.name = :hobby", Hobby.class);
            query.setParameter("hobby", hobby).executeUpdate();
            em.getTransaction().commit();
            //make catch if user == null
        } finally {
            em.close();
        }
       
    }
    
    public void addHobby(String Name, String Description) {
        EntityManager em = emf.createEntityManager();
        Hobby hobby = new Hobby(Name, Description);
        try {
              em.getTransaction().begin();
              em.persist(hobby);
            em.getTransaction().commit();
            //make catch if user == null
        } finally {
            em.close();
        }
       
    }
    
       public void editHobby(Long id, String Name, String Description) {
        EntityManager em = emf.createEntityManager();
      
        try {
            Hobby hobby = em.find(Hobby.class, id);
            hobby.setName(Name);
            hobby.setDescription(Description);
              em.getTransaction().begin();
              em.merge(hobby);
            em.getTransaction().commit();
            //make catch if user == null
        } finally {
            em.close();
        }
       
    }
       public Hobby getHobbyById(Long id) {
        EntityManager em = emf.createEntityManager();
        Hobby hobby;
        try {
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.id = :id", Hobby.class);
            hobby = query.setParameter("id", id).getResultList().get(0);
            //make catch if user == null
        } finally {
            em.close();
        }
        return hobby;
    }
       
       public void addPerson(String firstName, String lastName, String email, int phone) {
        EntityManager em = emf.createEntityManager();
        Person person = new Person(firstName, lastName, email, phone);
        try {
              em.getTransaction().begin();
              em.persist(person);
            em.getTransaction().commit();
            //make catch if user == null
        } finally {
            em.close();
        }
       
    }
       
         public void deletePerson(String name) {
        EntityManager em = emf.createEntityManager();
     
             Person person = getPersonByName(name);
             for (Hobby hobby : person.getHobbyes()) {
                 hobby.removePerson(person);
                 em.merge(hobby);
             }

        try {
            em.getTransaction().begin();
            person = em.find(Person.class, person.getId());
            int numOfPeople = person.getAddress().getPeople().size();
            if (numOfPeople == 1) {
                em.remove(person.getAddress());
            }

            em.remove(person);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new WebApplicationException("Could not delete person", 400);
        } finally {
            em.close();
        }
    }
         
            public void editPerson(Long id, String firstName, String lastName, String email, int phone, List<Hobby> hobbies, Address address) {
        EntityManager em = emf.createEntityManager();
        
              em.getTransaction().begin();
              Person person = em.find(Person.class, id);
              person.setFirstName(firstName);
              person.setLastName(lastName);
             
              
              person.setEmail(email);
              person.setPhone(phone);
              if(!address.getStreet().equals(person.getAddress().getStreet())) // add zip to if statement
              {
              int numOfPeople = person.getAddress().getPeople().size();
                if (numOfPeople == 1) {
                em.remove(person.getAddress());
            }
                person.setAddress(address);
              }
              
//               for (Hobby hobby : person.getHobbyes()) {
//                 hobby.removePerson(person);
//                 em.merge(hobby);
//             }
             //  person.setHobbyes(hobbies);
//                 for (Hobby hobby : person.getHobbyes()) {
//                 hobby.addPerson(person);
//                 em.merge(hobby);
//             }
          try {
              em.merge(person);
            em.getTransaction().commit();
            //make catch if user == null
        } finally {
            em.close();
        }
       
    }
}
