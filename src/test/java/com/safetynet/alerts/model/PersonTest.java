package com.safetynet.alerts.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PersonTest {

    private Person person1;
    private Person person2;
    private Person person3;

    @BeforeEach
    public void setUp() {
        person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Boyd");
        person1.setAddress("1509 Culver St");
        person1.setCity("Culver");
        person1.setZip("97451");
        person1.setPhone("841-874-6512");
        person1.setEmail("jaboyd@email.com");

        person2 = new Person();
        person2.setFirstName("John");
        person2.setLastName("Boyd");
        person2.setAddress("1509 Culver St");
        person2.setCity("Culver");
        person2.setZip("97451");
        person2.setPhone("841-874-6512");
        person2.setEmail("jaboyd@email.com");

        person3 = new Person();
        person3.setFirstName("Jacob");
        person3.setLastName("Boyd");
        person3.setAddress("1509 Culver St");
        person3.setCity("Culver");
        person3.setZip("97451");
        person3.setPhone("841-874-6513");
        person3.setEmail("drk@email.com");
    }

    @Test
    public void testEquals() {
        assertEquals(person1, person2);

        assertNotEquals(person1, person3);
    }

    @Test
    public void testHashCode() {
        assertEquals(person1.hashCode(), person2.hashCode()); 
    }

    @Test
    public void testToString() {
        String expectedToString = "Person(firstName=John, lastName=Boyd, address=1509 Culver St, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com, medicalRecord=null)";
        assertEquals(expectedToString, person1.toString());
    }
}

