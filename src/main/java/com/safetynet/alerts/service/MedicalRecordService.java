package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final List<MedicalRecord> medicalRecords = new ArrayList<>();

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord){
        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    public MedicalRecord updateMedicalrecord (String firstName, String lastName, MedicalRecord updatedMedicalRecord) {
        return medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .findFirst()
                .map(existingMedicalRecord -> {
                    existingMedicalRecord.setBirthDate(updatedMedicalRecord.getBirthDate());
                    existingMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
                    existingMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
                    return existingMedicalRecord;
                })
                .orElse(null);
    }

    public boolean deleteMedicalRecord( String firstName, String lastName) {
        return medicalRecords.removeIf(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecords;
    }
}
