package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FireServiceTest {

    private FireService fireService;
    private FireStationService fireStationService;
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        fireStationService = new FireStationService();
        personService = new PersonService();
        fireService = new FireService(fireStationService, personService);

        FireStation fireStation = new FireStation();
        fireStation.setAddress("20 Main Street");
        fireStation.setStation("1");
        fireStationService.addFireStation(fireStation);

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Paul");
        medicalRecord1.setLastName("Henri");
        medicalRecord1.setBirthdate("05/05/1990");
        medicalRecord1.setMedications(Arrays.asList("aznol:60mg", "hydrapermazol:900mg"));
        medicalRecord1.setAllergies(Arrays.asList("peanut"));

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Tom");
        medicalRecord2.setLastName("Henri");
        medicalRecord2.setBirthdate("05/05/1995");
        medicalRecord2.setMedications(Arrays.asList("aznol:60mg"));
        medicalRecord2.setAllergies(Arrays.asList("peanut"));

        Person person1 = new Person();
        person1.setFirstName("Paul");
        person1.setLastName("Henri");
        person1.setAddress("20 Main Street");
        person1.setPhone("123-456-7890");
        person1.setMedicalRecord(medicalRecord1);
        personService.addPerson(person1);

        Person person2 = new Person();
        person2.setFirstName("Tom");
        person2.setLastName("Henri");
        person2.setAddress("20 Main Street");
        person2.setPhone("111-111-1111");
        person2.setMedicalRecord(medicalRecord2);
        personService.addPerson(person2);
    }

    @Test
    public void testGetResidentsAndFireStationByAddress() {
        String address = "20 Main Street";
        List<Map<String, Object>> result = fireService.getResidentsAndFireStationByAddress(address);

        assertNotNull(result);
        assertEquals(3, result.size());

        Map<String, Object> fireStationInfo = result.get(0);
        assertEquals("1", fireStationInfo.get("fireStation"));

        Map<String, Object> residentInfo1 = result.get(1);
        assertEquals("Paul", residentInfo1.get("firstName"));
        assertEquals("Henri", residentInfo1.get("lastName"));
        assertEquals("123-456-7890", residentInfo1.get("phone"));
        assertEquals(33, residentInfo1.get("age"));
        assertTrue(((List<String>) residentInfo1.get("medications")).containsAll(Arrays.asList("aznol:60mg", "hydrapermazol:900mg")));
        assertTrue(((List<String>) residentInfo1.get("allergies")).contains("peanut"));

        Map<String, Object> residentInfo2 = result.get(2);
        assertEquals("Tom", residentInfo2.get("firstName"));
        assertEquals("Henri", residentInfo2.get("lastName"));
        assertEquals("111-111-1111", residentInfo2.get("phone"));
        assertEquals(28, residentInfo2.get("age"));
        assertTrue(((List<String>) residentInfo2.get("medications")).contains("aznol:60mg"));
        assertTrue(((List<String>) residentInfo2.get("allergies")).contains("peanut"));
    }
}
