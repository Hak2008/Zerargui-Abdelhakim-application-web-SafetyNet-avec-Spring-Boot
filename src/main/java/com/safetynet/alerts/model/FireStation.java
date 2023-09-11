package com.safetynet.alerts.model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "firestations")
@Data
public class FireStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    private String station;

}
