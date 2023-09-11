package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.FireStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    @GetMapping("/coverage")
    public ResponseEntity<Map<String, Object>> getFireStationCoverage(@RequestParam("stationNumber") int stationNumber) {
        List<Person> coveredPersons = fireStationService.getPersonsByStationNumber(stationNumber);

        if (coveredPersons.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        LocalDate currentDate = LocalDate.now();

        List<Map<String, String>> filteredPersons = coveredPersons.stream()
                .map(person -> {
                    Map<String, String> personInfo = new HashMap<>();
                    personInfo.put("firstName", person.getFirstName());
                    personInfo.put("lastName", person.getLastName());
                    personInfo.put("address", person.getAddress());
                    personInfo.put("phone", person.getPhone());
                    return personInfo;
                })
                .collect(Collectors.toList());

        long adultsCount = coveredPersons.stream()
                .filter(person -> person.getMedicalRecord() != null && person.getMedicalRecord().isAdult(currentDate))
                .count();

        long childrenCount = coveredPersons.size() - adultsCount;

        Map<String, Object> response = new HashMap<>();
        response.put("coveredPersons", filteredPersons);
        response.put("numberOfAdults", adultsCount);
        response.put("numberOfChildren", childrenCount);

        return ResponseEntity.ok(response);
    }


}
