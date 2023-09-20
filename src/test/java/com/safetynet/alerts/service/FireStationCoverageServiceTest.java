package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FireStationCoverageServiceTest {

    private FireStationCoverageService fireStationCoverageService;
    private PersonService personService;
    private FireStationService fireStationService;

    @BeforeEach
    public void setUp() {
        personService = new PersonService();
        fireStationService = new FireStationService();
        fireStationCoverageService = new FireStationCoverageService(personService, fireStationService);

        Person person1 = new Person();
        person1.setFirstName("Tom");
        person1.setLastName("Henri");
        person1.setAddress("20 Main Street");
        person1.setPhone("123-456-7890");
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setBirthdate("05/05/2000");
        person1.setMedicalRecord(medicalRecord1);
        personService.addPerson(person1);

        Person person2 = new Person();
        person2.setFirstName("Paul");
        person2.setLastName("Henri");
        person2.setAddress("22 Street");
        person2.setPhone("987-654-3210");
        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setBirthdate("05/20/2010");
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
    public void testGetPeopleCoveredByStation() {

        String stationNumber1 = "1";
        Map<String, Object> result1 = fireStationCoverageService.getPeopleCoveredByStation(stationNumber1);

        assertNotNull(result1);
        List<Map<String, String>> persons1 = (List<Map<String, String>>) result1.get("persons");
        int numberOfAdults1 = (int) result1.get("numberOfAdults");
        int numberOfChildren1 = (int) result1.get("numberOfChildren");

        assertEquals(1, persons1.size());

        Map<String, String> person1 = persons1.get(0);
        assertNotNull(person1);
        assertEquals("Tom", person1.get("firstName"));
        assertEquals("Henri", person1.get("lastName"));
        assertEquals("20 Main Street", person1.get("address"));
        assertEquals("123-456-7890", person1.get("phone"));

        assertEquals(1, numberOfAdults1);
        assertEquals(0, numberOfChildren1);

        String stationNumber2 = "2";
        Map<String, Object> result2 = fireStationCoverageService.getPeopleCoveredByStation(stationNumber2);

        assertNotNull(result2);
        List<Map<String, String>> persons2 = (List<Map<String, String>>) result2.get("persons");
        int numberOfAdults2 = (int) result2.get("numberOfAdults");
        int numberOfChildren2 = (int) result2.get("numberOfChildren");

        assertEquals(1, persons2.size());

        Map<String, String> person2 = persons2.get(0);
        assertNotNull(person2);
        assertEquals("Paul", person2.get("firstName"));
        assertEquals("Henri", person2.get("lastName"));
        assertEquals("22 Street", person2.get("address"));
        assertEquals("987-654-3210", person2.get("phone"));

        assertEquals(0, numberOfAdults2);
        assertEquals(1, numberOfChildren2);
    }
}
