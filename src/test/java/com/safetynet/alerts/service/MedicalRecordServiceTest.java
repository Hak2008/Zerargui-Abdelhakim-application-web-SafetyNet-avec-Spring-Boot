package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalRecordServiceTest {

    private MedicalRecordService medicalRecordService;

    @BeforeEach
    public void setUp() {
        medicalRecordService = new MedicalRecordService();
    }

    @Test
    public void testAddMedicalRecord() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Tom");
        medicalRecord.setLastName("Henri");

        MedicalRecord addedMedicalRecord = medicalRecordService.addMedicalRecord(medicalRecord);

        assertNotNull(addedMedicalRecord);
        assertEquals("Tom", addedMedicalRecord.getFirstName());
        assertEquals("Henri", addedMedicalRecord.getLastName());
        assertTrue(medicalRecordService.getAllMedicalRecords().contains(medicalRecord));
    }

    @Test
    public void testUpdateMedicalRecord() {
        MedicalRecord existingMedicalRecord = new MedicalRecord();
        existingMedicalRecord.setFirstName("Tom");
        existingMedicalRecord.setLastName("Henri");
        existingMedicalRecord.setBirthdate("01/01/1980");
        medicalRecordService.addMedicalRecord(existingMedicalRecord);

        MedicalRecord updatedMedicalRecord = new MedicalRecord();
        updatedMedicalRecord.setBirthdate("02/02/1990");

        MedicalRecord result = medicalRecordService.updateMedicalRecord("Tom", "Henri", updatedMedicalRecord);

        assertNotNull(result);
        assertEquals("Tom", result.getFirstName());
        assertEquals("Henri", result.getLastName());
        assertEquals("02/02/1990", result.getBirthdate());
        assertTrue(medicalRecordService.getAllMedicalRecords().contains(result));
    }

    @Test
    public void testDeleteMedicalRecord() {
        MedicalRecord medicalRecordToDelete = new MedicalRecord();
        medicalRecordToDelete.setFirstName("Tom");
        medicalRecordToDelete.setLastName("Henri");
        medicalRecordService.addMedicalRecord(medicalRecordToDelete);

        boolean deleted = medicalRecordService.deleteMedicalRecord("Tom", "Henri");

        assertTrue(deleted);
        assertFalse(medicalRecordService.getAllMedicalRecords().contains(medicalRecordToDelete));
    }

    @Test
    public void testGetAllMedicalRecords() {
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Tom");
        medicalRecord1.setLastName("Henri");
        medicalRecordService.addMedicalRecord(medicalRecord1);

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Paul");
        medicalRecord2.setLastName("Henri");
        medicalRecordService.addMedicalRecord(medicalRecord2);

        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecords();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}

