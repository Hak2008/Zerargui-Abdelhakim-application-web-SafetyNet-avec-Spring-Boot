package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("POST request received: Adding a medical record.");
        MedicalRecord addedMedicalRecord = medicalRecordService.addMedicalRecord(medicalRecord);
        log.info("Medical record added successfully.");
        return new ResponseEntity<>(addedMedicalRecord, HttpStatus.CREATED);
    }

    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody MedicalRecord updatedMedicalRecord) {
        log.info("PUT request received: Updating a medical record for {} {}.", firstName, lastName);
        MedicalRecord medicalRecord = medicalRecordService.updateMedicalRecord(firstName, lastName, updatedMedicalRecord);
        if (medicalRecord != null) {
            log.info("Medical record updated successfully.");
            return ResponseEntity.ok(medicalRecord);
        } else {
            log.info("No medical record found for {} {}.", firstName, lastName);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        log.info("DELETE request received: Deleting a medical record for {} {}.", firstName, lastName);
        boolean deleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (deleted) {
            log.info("Medical record deleted successfully.");
            return ResponseEntity.noContent().build();
        } else {
            log.info("No medical record found for {} {}.", firstName, lastName);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        log.info("GET request received: Getting all medical records.");
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        log.info("Reply sent with status: " + HttpStatus.OK);
        return ResponseEntity.ok(medicalRecords);
    }
}