package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FireStationServiceTest {

    private FireStationService fireStationService;

    @BeforeEach
    public void setUp() {
        fireStationService = new FireStationService();
        FireStation existingFireStation = new FireStation();
        existingFireStation.setAddress("21 Main Street");
        existingFireStation.setStation("1");
        fireStationService.addFireStation(existingFireStation);
    }

    @Test
    public void testAddFireStation() {
        FireStation fireStationToAdd = new FireStation();
        fireStationToAdd.setAddress("22 Main Street");
        fireStationToAdd.setStation("2");

        FireStation addedFireStation = fireStationService.addFireStation(fireStationToAdd);

        assertNotNull(addedFireStation);
        assertEquals("22 Main Street", addedFireStation.getAddress());
        assertEquals("2", addedFireStation.getStation());
        assertTrue(fireStationService.getAllFireStations().contains(addedFireStation));
    }

    @Test
    public void testUpdateFireStation() {
        FireStation updatedFireStation = new FireStation();
        updatedFireStation.setStation("3");

        FireStation updated = fireStationService.updateFireStation("21 Main Street", updatedFireStation);

        assertNotNull(updated);
        assertEquals("3", updated.getStation());
        assertEquals("21 Main Street", updated.getAddress());
        assertTrue(fireStationService.getAllFireStations().contains(updated));
    }

    @Test
    public void testDeleteFireStation() {
        boolean deleted = fireStationService.deleteFireStation("21 Main Street");

        assertTrue(deleted);
        assertNull(fireStationService.updateFireStation("21 Main Street", new FireStation()));
        assertFalse(fireStationService.getAllFireStations().stream().anyMatch(fireStation ->
                "21 Main Street".equals(fireStation.getAddress())));
    }

    @Test
    public void testGetAllFireStations() {
        List<FireStation> allFireStations = fireStationService.getAllFireStations();

        assertNotNull(allFireStations);
        assertEquals(1, allFireStations.size());
    }
}
