package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "medicalrecords")
@Data
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private String birthdate;

    private List<String> medications;
    private List<String> allergies;

    @OneToOne(mappedBy = "medicalRecord", cascade = CascadeType.ALL)
    private Person person;

    public boolean isAdult() {
        if (birthdate != null) {
            LocalDate birthdate = LocalDate.parse(this.birthdate);
            LocalDate currentDate = LocalDate.now();
            int age = Period.between(birthdate, currentDate).getYears();
            return age > 18;
        } else {
            return false;
        }
    }
 }
