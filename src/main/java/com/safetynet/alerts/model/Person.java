package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "persons")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medical_record_id")
    private MedicalRecord medicalRecord;

}
