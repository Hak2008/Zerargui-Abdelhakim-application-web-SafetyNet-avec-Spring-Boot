package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordService {

    private final List<MedicalRecord> medicalRecords = new ArrayList<>();

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord){
        log.debug("Adding medical record: {}", medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
        medicalRecords.add(medicalRecord);
        log.info("Medical record added successfully: {}", medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
        return medicalRecord;
    }

    public MedicalRecord updateMedicalRecord (String firstName, String lastName, MedicalRecord updatedMedicalRecord) {
        log.debug("Updating medical record for: {}", firstName + " " + lastName);
        return medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .findFirst()
                .map(existingMedicalRecord -> {
                    existingMedicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
                    existingMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
                    existingMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
                    return existingMedicalRecord;
                })
                .orElse(null);
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        log.debug("Deleting medical record for {} {}", firstName, lastName);
        boolean result = medicalRecords.removeIf(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
        if (result) {
            log.info("Medical record deleted successfully for {} {}", firstName, lastName);
        } else {
            log.error("Failed to delete medical record for {} {}", firstName, lastName);
        }
        return result;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        log.debug("Retrieving all medical records");
        return medicalRecords;
    }
}