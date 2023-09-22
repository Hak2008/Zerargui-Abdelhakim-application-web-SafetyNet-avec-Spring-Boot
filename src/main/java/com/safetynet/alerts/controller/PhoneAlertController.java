package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.PhoneAlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertController {

    private final PhoneAlertService phoneAlertService;

    @Autowired
    public PhoneAlertController (PhoneAlertService phoneAlertService){

        this.phoneAlertService = phoneAlertService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getPhoneAlertByFireStation(@RequestParam("firestation") String fireStationNumber) {
        log.info("GET request received: Get phone alert for firestation {}", fireStationNumber);

        List<String> phoneNumbers = phoneAlertService.getPhoneAlertByFireStation(fireStationNumber);

        if (phoneNumbers.isEmpty()) {
            log.info("No residents found for firestation number {}", fireStationNumber);
            return ResponseEntity.ok(Collections.emptyList());
        }
        log.info("Reply sent with status: " + HttpStatus.OK);
        return ResponseEntity.ok(phoneNumbers);
    }
}
