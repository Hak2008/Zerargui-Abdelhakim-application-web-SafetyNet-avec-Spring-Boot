package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.FireStationCoverageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/firestation/coverage")
public class FireStationCoverageController {

    private final FireStationCoverageService fireStationCoverageService;

    @Autowired
    public FireStationCoverageController(FireStationCoverageService fireStationCoverageService) {

        this.fireStationCoverageService = fireStationCoverageService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPeopleCoveredByStation(@RequestParam("stationNumber") String stationNumber) {
        log.info("GET request received: Get people by station number {}", stationNumber);

        Map<String, Object> result = fireStationCoverageService.getPeopleCoveredByStation(stationNumber);

        if (result.get("persons") == null) {
            log.info("No fire station found with station number {}", stationNumber);
            return ResponseEntity.notFound().build();
        }
        log.info("Reply sent with status: " + HttpStatus.OK);
        return ResponseEntity.ok(result);
    }
}
