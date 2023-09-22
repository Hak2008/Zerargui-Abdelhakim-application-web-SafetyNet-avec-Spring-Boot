package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneAlertServiceTest {

    private PhoneAlertService phoneAlertService;
    private PersonService personService;
    private FireStationService fireStationService;

    @BeforeEach
    public void setUp() {
        personService = new PersonService();
        fireStationService = new FireStationService();
        phoneAlertService = new PhoneAlertService(fireStationService, personService);

        Person person1 = new Person();
        person1.setFirstName("Tom");
        person1.setLastName("Henri");
        person1.setAddress("20 Main Street");
        person1.setPhone("123-456-7890");
        personService.addPerson(person1);

        Person person2 = new Person();
        person2.setFirstName("Paul");
        person2.setLastName("Henri");
        person2.setAddress("20 Main Street");
        person2.setPhone("987-654-3210");
        personService.addPerson(person2);

        Person person3 = new Person();
        person3.setFirstName("Alice");
        person3.setLastName("Smith");
        person3.setAddress("22 Street");
        person3.setPhone("555-123-4567");
        personService.addPerson(person3);

        FireStation fireStation1 = new FireStation();
        fireStation1.setAddress("20 Main Street");
        fireStation1.setStation("1");
        fireStationService.addFireStation(fireStation1);

        FireStation fireStation2 = new FireStation();
        fireStation2.setAddress("22 Street");
        fireStation2.setStation("2");
        fireStationService.addFireStation(fireStation2);
    }

    @Test
    public void testGetPhoneAlertByFireStation() {
        String stationNumber1 = "1";
        List<String> phoneNumbers1 = phoneAlertService.getPhoneAlertByFireStation(stationNumber1);

        assertNotNull(phoneNumbers1);
        assertEquals(2, phoneNumbers1.size());
        assertTrue(phoneNumbers1.contains("123-456-7890"));
        assertTrue(phoneNumbers1.contains("987-654-3210"));

        String stationNumber2 = "2";
        List<String> phoneNumbers2 = phoneAlertService.getPhoneAlertByFireStation(stationNumber2);

        assertNotNull(phoneNumbers2);
        assertEquals(1, phoneNumbers2.size());
        assertTrue(phoneNumbers2.contains("555-123-4567"));
    }
}

