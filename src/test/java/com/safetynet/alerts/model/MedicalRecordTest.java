package com.safetynet.alerts.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MedicalRecordTest {

    private MedicalRecord medicalRecord1;
    private MedicalRecord medicalRecord2;
    private MedicalRecord medicalRecord3;

    @BeforeEach
    public void setUp() {
        medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("John");
        medicalRecord1.setLastName("Boyd");
        medicalRecord1.setBirthdate("03/06/1984");
        List<String> medications1 = new ArrayList<>();
        medications1.add("aznol:350mg");
        medications1.add("hydrapermazol:100mg");
        medicalRecord1.setMedications(medications1);
        List<String> allergies1 = new ArrayList<>();
        allergies1.add("nillacilan");
        medicalRecord1.setAllergies(allergies1);

        medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Jacob");
        medicalRecord2.setLastName("Boyd");
        medicalRecord2.setBirthdate("03/06/1989");
        List<String> medications2 = new ArrayList<>();
        medications2.add("pharmacol:5000mg");
        medications2.add("terazine:10mg");
        medications2.add("noznazol:250mg");
        medicalRecord2.setMedications(medications2);
        List<String> allergies2 = new ArrayList<>();
        medicalRecord2.setAllergies(allergies2);

        medicalRecord3 = new MedicalRecord();
        medicalRecord3.setFirstName("John");
        medicalRecord3.setLastName("Smith");
        medicalRecord3.setBirthdate("03/06/1984");
        List<String> medications3 = new ArrayList<>();
        medications3.add("aznol:350mg");
        medications3.add("hydrapermazol:100mg");
        medicalRecord3.setMedications(medications3);
        List<String> allergies3 = new ArrayList<>();
        allergies3.add("nillacilan");
        medicalRecord3.setAllergies(allergies3);
    }

    @Test
    public void testEquals() {

        assertEquals(medicalRecord1, medicalRecord1);
        assertNotEquals(medicalRecord1, medicalRecord2);
        assertNotEquals(medicalRecord1, medicalRecord3);
        assertNotEquals(medicalRecord2, medicalRecord3);
    }

    @Test
    public void testHashCode() {

        assertEquals(medicalRecord1.hashCode(), medicalRecord1.hashCode());
        assertNotEquals(medicalRecord1.hashCode(), medicalRecord2.hashCode());
        assertNotEquals(medicalRecord1.hashCode(), medicalRecord3.hashCode());
        assertNotEquals(medicalRecord2.hashCode(), medicalRecord3.hashCode());
    }

    @Test
    public void testToString() {

        assertNotNull(medicalRecord1.toString());
        assertTrue(medicalRecord1.toString().contains("John"));
        assertTrue(medicalRecord1.toString().contains("Boyd"));
        assertTrue(medicalRecord1.toString().contains("03/06/1984"));
        assertTrue(medicalRecord1.toString().contains("aznol:350mg"));
        assertTrue(medicalRecord1.toString().contains("hydrapermazol:100mg"));
        assertTrue(medicalRecord1.toString().contains("nillacilan"));
    }

    @Test
    public void testGetAge() {

        assertEquals(39, medicalRecord1.getAge());
        assertEquals(34, medicalRecord2.getAge());
        assertEquals(39, medicalRecord3.getAge());
    }
}
