package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        FireStation addedFireStation = fireStationService.addFireStation(fireStation);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedFireStation);
    }

    @PutMapping("/{address}")
    public ResponseEntity<FireStation> updateFireStation(
            @PathVariable String address,
            @RequestBody FireStation updateFireStation) {
        FireStation fireStation = fireStationService.updateFireStation(address, updateFireStation);
        if (fireStation != null) {
            return ResponseEntity.ok(fireStation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{address}")
    public ResponseEntity<Void> deleteFireStation(
            @PathVariable String address){
        boolean deleted = fireStationService.deleteFireStation(address);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<FireStation>> getAllFireStations() {
        List<FireStation> fireStations = fireStationService.getAllFireStations();
        return ResponseEntity.ok(fireStations);
    }
}
