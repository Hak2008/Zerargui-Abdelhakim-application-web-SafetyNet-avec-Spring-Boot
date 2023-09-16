package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.FloodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/flood/stations")
public class FloodController {

    private final FloodService floodService;

    @Autowired
    public FloodController(FloodService floodService) {
        this.floodService = floodService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getHomesServedByStations(@RequestParam("stations") List<String> stationNumbers) {
        log.info("GET request received: Get homes served by stations {}", stationNumbers);

        List<Map<String, Object>> homesServedByStations = floodService.getHomesServedByStations(stationNumbers);

        if (homesServedByStations.isEmpty()) {
            log.info("No homes found for stations {}", stationNumbers);
            return ResponseEntity.notFound().build();
        }
        log.info("Reply sent with status: " + HttpStatus.OK);
        return ResponseEntity.ok(homesServedByStations);
    }
}
