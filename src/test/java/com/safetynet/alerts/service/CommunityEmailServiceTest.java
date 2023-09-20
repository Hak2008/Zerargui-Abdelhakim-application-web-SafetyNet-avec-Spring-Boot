package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommunityEmailServiceTest {

    private CommunityEmailService communityEmailService;
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        personService = new PersonService();
        communityEmailService = new CommunityEmailService(personService);
    }

    @Test
    public void testGetEmailsByCity() {
        Person person1 = new Person();
        person1.setFirstName("Paul");
        person1.setLastName("Henri");
        person1.setCity("New York");
        person1.setEmail("paul.henri@email.com");
        personService.addPerson(person1);

        Person person2 = new Person();
        person2.setFirstName("Tom");
        person2.setLastName("Henri");
        person2.setCity("New York");
        person2.setEmail("tom.henri@email.com");
        personService.addPerson(person2);

        Person person3 = new Person();
        person3.setFirstName("Jeanine");
        person3.setLastName("Henri");
        person3.setCity("Los Angeles");
        person3.setEmail("jeanine.henri@email.com");
        personService.addPerson(person3);

        String city = "New York";
        List<String> emailsInCity = communityEmailService.getEmailsByCity(city);

        assertNotNull(emailsInCity);
        assertEquals(2, emailsInCity.size());
        assertTrue(emailsInCity.containsAll(Arrays.asList("paul.henri@email.com", "tom.henri@email.com")));
    }
}
