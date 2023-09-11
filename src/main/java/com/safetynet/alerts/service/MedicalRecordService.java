package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updatedMedicalRecord) {
        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);

        if (optionalMedicalRecord.isPresent()) {
            MedicalRecord existingMedicalRecord = optionalMedicalRecord.get();
            existingMedicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
            existingMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
            existingMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
            return medicalRecordRepository.save(existingMedicalRecord);
        } else {
            return null;
        }
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);

        if (optionalMedicalRecord.isPresent()) {
            medicalRecordRepository.delete(optionalMedicalRecord.get());
            return true;
        } else {
            return false;
        }
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }
}