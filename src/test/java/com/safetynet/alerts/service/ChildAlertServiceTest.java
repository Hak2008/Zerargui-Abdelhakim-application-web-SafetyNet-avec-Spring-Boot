package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ChildAlertServiceTest {

    private ChildAlertService childAlertService;
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        personService = new PersonService();
        childAlertService = new ChildAlertService(personService);
    }

    @Test
    public void testGetChildAlertForAddress() {
        Person child1 = new Person();
        child1.setFirstName("Paul");
        child1.setLastName("Henri");
        child1.setAddress("20 Main Street");
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setBirthdate("05/05/2012");
        child1.setMedicalRecord(medicalRecord1);
        personService.addPerson(child1);

        Person child2 = new Person();
        child2.setFirstName("Tom");
        child2.setLastName("Henri");
        child2.setAddress("20 Main Street");
        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setBirthdate("05/05/2010");
        child2.setMedicalRecord(medicalRecord2);
        personService.addPerson(child2);

        Person adult1 = new Person();
        adult1.setFirstName("Jeanine");
        adult1.setLastName("Henri");
        adult1.setAddress("20 Main Street");
        MedicalRecord medicalRecord3 = new MedicalRecord();
        medicalRecord3.setBirthdate("05/05/1980");
        adult1.setMedicalRecord(medicalRecord3);
        personService.addPerson(adult1);

        String address = "20 Main Street";
        List<Map<String, Object>> childAlertList = childAlertService.getChildAlertForAddress(address);

        assertNotNull(childAlertList);
        assertEquals(2, childAlertList.size());

        for (Map<String, Object> childDetails : childAlertList) {
            assertTrue(childDetails.containsKey("firstName"));
            assertTrue(childDetails.containsKey("lastName"));
            assertTrue(childDetails.containsKey("age"));
            assertTrue(childDetails.containsKey("otherHouseholdMembers"));

            String firstName = (String) childDetails.get("firstName");
            String lastName = (String) childDetails.get("lastName");
            int age = (int) childDetails.get("age");
            List<String> otherHouseholdMembers = (List<String>) childDetails.get("otherHouseholdMembers");

            assertTrue(Arrays.asList("Paul", "Tom").contains(firstName));
            assertEquals("Henri", lastName);
            assertTrue(age <= 18);

            if (firstName.equals("Paul")) {
                assertEquals(11, age);
                assertTrue(otherHouseholdMembers.contains("Tom Henri"));
                assertTrue(otherHouseholdMembers.contains("Jeanine Henri"));
            } else if (firstName.equals("Tom")) {
                assertEquals(13, age);
                assertTrue(otherHouseholdMembers.contains("Paul Henri"));
                assertTrue(otherHouseholdMembers.contains("Jeanine Henri"));
            }
        }
    }
}
