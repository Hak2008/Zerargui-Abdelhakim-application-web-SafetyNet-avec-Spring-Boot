package com.safetynet.alerts.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private List<String> medications;
    private List<String> allergies;

}
