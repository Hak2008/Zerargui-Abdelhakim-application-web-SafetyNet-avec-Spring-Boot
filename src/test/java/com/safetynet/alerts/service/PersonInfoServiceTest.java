package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonInfoServiceTest {

    private PersonService personService;
    private PersonInfoService personInfoService;

    @BeforeEach
    public void setUp() {
        personService = new PersonService();
        personInfoService = new PersonInfoService(personService);

        Person person1 = new Person();
        person1.setFirstName("Paul");
        person1.setLastName("Henri");
        person1.setAddress("20 Main Street");
        person1.setEmail("paul@email.com");
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setBirthdate("05/05/1990");
        medicalRecord1.setMedications(Arrays.asList("aznol:60mg", "hydrapermazol:900mg"));
        medicalRecord1.setAllergies(Arrays.asList("peanut"));
        person1.setMedicalRecord(medicalRecord1);
        personService.addPerson(person1);

        Person person2 = new Person();
        person2.setFirstName("Tom");
        person2.setLastName("Henri");
        person2.setAddress("20 Main Street");
        person2.setEmail("tom@email.com");
        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setBirthdate("05/05/1995");
        medicalRecord2.setMedications(Arrays.asList("aznol:60mg"));
        medicalRecord2.setAllergies(Arrays.asList("peanut"));
        person2.setMedicalRecord(medicalRecord2);
        personService.addPerson(person2);
    }

    @Test
    public void testGetPersonInfoByLastName() {
        List<Map<String, Object>> result = personInfoService.getPersonInfoByLastName("Henri");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Henri", result.get(0).get("lastName"));
        List<Map<String, Object>> personsInfo = (List<Map<String, Object>>) result.get(0).get("persons");
        assertEquals(2, personsInfo.size());

        Map<String, Object> personInfo1 = personsInfo.get(0);
        assertEquals("Paul", personInfo1.get("firstName"));
        assertEquals("Henri", personInfo1.get("lastName"));
        assertEquals("20 Main Street", personInfo1.get("address"));
        assertEquals("paul@email.com", personInfo1.get("email"));
        assertEquals(33, personInfo1.get("age"));
        assertEquals("05/05/1990", personInfo1.get("birthdate"));
        assertEquals(Arrays.asList("aznol:60mg", "hydrapermazol:900mg"), personInfo1.get("medications"));
        assertEquals(Arrays.asList("peanut"), personInfo1.get("allergies"));

        Map<String, Object> personInfo2 = personsInfo.get(1);
        assertEquals("Tom", personInfo2.get("firstName"));
        assertEquals("Henri", personInfo2.get("lastName"));
        assertEquals("20 Main Street", personInfo2.get("address"));
        assertEquals("tom@email.com", personInfo2.get("email"));
        assertEquals(28, personInfo2.get("age"));
        assertEquals("05/05/1995", personInfo2.get("birthdate"));
        assertEquals(Arrays.asList("aznol:60mg"), personInfo2.get("medications"));
        assertEquals(Arrays.asList("peanut"), personInfo2.get("allergies"));
    }
}
