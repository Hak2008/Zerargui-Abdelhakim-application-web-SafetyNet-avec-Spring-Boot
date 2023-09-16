package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private final FireStationService fireStationService;

    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;

    }

    @PostMapping
    public ResponseEntity<FireStation> addFireStation(@RequestBody FireStation fireStation) {
        log.info("POST request received: Adding a fire station.");
        FireStation addedFireStation = fireStationService.addFireStation(fireStation);
        log.info("Reply sent with status: " + HttpStatus.CREATED);
        return new ResponseEntity<>(addedFireStation, HttpStatus.CREATED);
    }

    @PutMapping("/{address}")
    public ResponseEntity<FireStation> updateFireStation(
            @PathVariable String address,
            @RequestBody FireStation updateFireStation) {
        log.info("PUT request received: Updating a fire station for address {}", address);
        FireStation fireStation = fireStationService.updateFireStation(address, updateFireStation);
        if (fireStation != null) {
            log.info("Fire station updated successfully.");
            return ResponseEntity.ok(fireStation);
        } else {
            log.info("No fire station found for address {}", address);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{address}")
    public ResponseEntity<Void> deleteFireStation(
            @PathVariable String address){
        log.info("DELETE request received: Deleting a fire station for address {}", address);
        boolean deleted = fireStationService.deleteFireStation(address);
        if (deleted) {
            log.info("Fire station deleted successfully.");
            return ResponseEntity.noContent().build();
        } else{
            log.info("No fire station found for address {}", address);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<FireStation>> getAllFireStations() {
        log.info("GET request received: Getting all fire stations.");
        List<FireStation> fireStations = fireStationService.getAllFireStations();
        log.info("Reply sent with status: " + HttpStatus.OK);
        return ResponseEntity.ok(fireStations);
    }
}
