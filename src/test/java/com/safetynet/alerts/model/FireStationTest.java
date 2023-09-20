package com.safetynet.alerts.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FireStationTest {

    private FireStation fireStation1;
    private FireStation fireStation2;
    private FireStation fireStation3;

    @BeforeEach
    public void setUp() {
        fireStation1 = new FireStation();
        fireStation1.setAddress("1509 Culver St");
        fireStation1.setStation("3");

        fireStation2 = new FireStation();
        fireStation2.setAddress("1509 Culver St");
        fireStation2.setStation("3");

        fireStation3 = new FireStation();
        fireStation3.setAddress("29 15th St");
        fireStation3.setStation("2");
    }

    @Test
    public void testEquals() {
        assertEquals(fireStation1, fireStation2);

        assertNotEquals(fireStation1, fireStation3);
    }

    @Test
    public void testHashCode() {

        assertEquals(fireStation1.hashCode(), fireStation2.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "FireStation(address=1509 Culver St, station=3)";
        assertEquals(expectedToString, fireStation1.toString());
    }
}

