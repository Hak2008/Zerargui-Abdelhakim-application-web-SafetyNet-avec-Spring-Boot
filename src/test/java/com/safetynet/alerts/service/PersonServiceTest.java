package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
    private PersonService personService;

    @BeforeEach
    public void setUp() {

        personService = new PersonService();
    }

    @Test
    public void testAddPerson() {
        Person person = new Person();
        person.setFirstName("Paul");
        person.setLastName("Henri");

        Person addedPerson = personService.addPerson(person);

        assertNotNull(addedPerson);
        assertEquals("Paul", addedPerson.getFirstName());
        assertEquals("Henri", addedPerson.getLastName());
        assertTrue(personService.getAllPersons().contains(person));
    }

    @Test
    public void testUpdatePerson() {
        Person existingPerson = new Person();
        existingPerson.setFirstName("Paul");
        existingPerson.setLastName("Henri");
        existingPerson.setAddress("20 Main Street");
        personService.addPerson(existingPerson);

        Person updatedPerson = new Person();
        updatedPerson.setAddress("10 Main Street");

        Person result = personService.updatePerson("Paul", "Henri", updatedPerson);

        assertNotNull(result);
        assertEquals("Paul", result.getFirstName());
        assertEquals("Henri", result.getLastName());
        assertEquals("10 Main Street", result.getAddress());
        assertTrue(personService.getAllPersons().contains(result));
    }

    @Test
    public void testDeletePerson() {
        Person personToDelete = new Person();
        personToDelete.setFirstName("Paul");
        personToDelete.setLastName("Henri");
        personService.addPerson(personToDelete);

        boolean deleted = personService.deletePerson("Paul", "Henri");

        assertTrue(deleted);
        assertFalse(personService.getAllPersons().contains(personToDelete));
    }

    @Test
    public void testGetAllPersons() {
        Person person1 = new Person();
        person1.setFirstName("Paul");
        person1.setLastName("Henri");
        personService.addPerson(person1);

        Person person2 = new Person();
        person2.setFirstName("Tom");
        person2.setLastName("Henri");
        personService.addPerson(person2);

        List<Person> result = personService.getAllPersons();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}

