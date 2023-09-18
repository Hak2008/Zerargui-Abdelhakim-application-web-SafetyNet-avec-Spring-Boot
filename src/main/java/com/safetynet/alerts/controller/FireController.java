package com.safetynet.alerts.controller;


import com.safetynet.alerts.service.FireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/fire")
public class FireController {


    private final FireService fireService;

    @Autowired
    public FireController(FireService fireService) {

        this.fireService = fireService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getResidentsAndFireStationByAddress(@RequestParam("address") String address) {
        log.info("GET request received: Get residents and fire station for address {}", address);

        List<Map<String, Object>> result = fireService.getResidentsAndFireStationByAddress(address);

        if (result.isEmpty()) {
            log.info("No residents found for address {}", address);
            return ResponseEntity.ok(Collections.emptyList());
        }
        log.info("Reply sent with status: " + HttpStatus.OK);
        return ResponseEntity.ok(result);
    }
}
