package com.safetynet.alerts.service;

import com.safetynet.alerts.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FloodServiceTest {

    private FloodService floodService;
    private PersonService personService;
    private FireStationService fireStationService;

    @BeforeEach
    public void setUp() {
        personService = new PersonService();
        fireStationService = new FireStationService();
        floodService = new FloodService(personService, fireStationService);

        Person person1 = new Person();
        person1.setFirstName("Tom");
        person1.setLastName("Henri");
        person1.setAddress("20 Main Street");
        person1.setPhone("123-456-789");
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setBirthdate("01/05/2000");
        medicalRecord1.setMedications(List.of("aznol:60mg", "hydrapermazol:900mg"));
        medicalRecord1.setAllergies(List.of("peanut", "shellfish"));
        person1.setMedicalRecord(medicalRecord1);
        personService.addPerson(person1);

        Person person2 = new Person();
        person2.setFirstName("Paul");
        person2.setLastName("Henri");
        person2.setAddress("22 Street");
        person2.setPhone("111-111-1111");
        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setBirthdate("03/05/1995");
        medicalRecord2.setMedications(List.of("noxidian:100mg", "pharmacol:2500mg"));
        medicalRecord2.setAllergies(List.of("shellfish", "aznol"));
        person2.setMedicalRecord(medicalRecord2);
        personService.addPerson(person2);

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
    public void testGetHomesServedByStations() {
        List<String> stationNumbers = new ArrayList<>();
        stationNumbers.add("1");
        stationNumbers.add("2");

        List<Map<String, Object>> homesServed = floodService.getHomesServedByStations(stationNumbers);

        assertNotNull(homesServed);
        assertEquals(2, homesServed.size());

        for (Map<String, Object> homeInfo : homesServed) {
            String address = (String) homeInfo.get("address");
            List<Map<String, Object>> residents = (List<Map<String, Object>>) homeInfo.get("residents");

            assertNotNull(address);
            assertNotNull(residents);

            if (address.equals("20 Main Street")) {
                assertEquals(1, residents.size());
                for (Map<String, Object> residentInfo : residents) {
                    String firstName = (String) residentInfo.get("firstName");
                    String lastName = (String) residentInfo.get("lastName");
                    String phone = (String) residentInfo.get("phone");
                    int age = (int) residentInfo.get("age");
                    List<String> medications = (List<String>) residentInfo.get("medications");
                    List<String> allergies = (List<String>) residentInfo.get("allergies");

                    assertNotNull(firstName);
                    assertNotNull(lastName);
                    assertNotNull(phone);
                    assertNotNull(age);
                    assertNotNull(medications);
                    assertNotNull(allergies);

                    if (firstName.equals("Tom")) {
                        assertEquals("Henri", lastName);
                        assertEquals("123-456-789", phone);
                        assertEquals(23, age);
                        assertEquals(2, medications.size());
                        assertEquals(2, allergies.size());
                    }
                }
            } else if (address.equals("22 Street")) {
                assertEquals(1, residents.size());
                for (Map<String, Object> residentInfo : residents) {
                    String firstName = (String) residentInfo.get("firstName");
                    String lastName = (String) residentInfo.get("lastName");
                    String phone = (String) residentInfo.get("phone");
                    int age = (int) residentInfo.get("age");
                    List<String> medications = (List<String>) residentInfo.get("medications");
                    List<String> allergies = (List<String>) residentInfo.get("allergies");

                    assertNotNull(firstName);
                    assertNotNull(lastName);
                    assertNotNull(phone);
                    assertNotNull(age);
                    assertNotNull(medications);
                    assertNotNull(allergies);

                    if (firstName.equals("Paul")) {
                        assertEquals("Henri", lastName);
                        assertEquals("111-111-1111", phone);
                        assertEquals(28, age);
                        assertEquals(2, medications.size());
                        assertEquals(2, allergies.size());
                    }
                }
            }
        }
    }
}
