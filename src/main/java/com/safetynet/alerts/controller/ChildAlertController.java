package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.ChildAlertService;
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
@RequestMapping("/childAlert")
public class ChildAlertController {

    private final ChildAlertService childAlertService;

    @Autowired
    public ChildAlertController(ChildAlertService childAlertService) {

        this.childAlertService = childAlertService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getChildAlertForAddress(@RequestParam("address") String address) {
        log.info("GET request received: Get child alert for address {}", address);

        List<Map<String, Object>> result = childAlertService.getChildAlertForAddress(address);

        if (result.isEmpty()) {
            log.info("No children found at address {}", address);
            return ResponseEntity.ok(Collections.emptyList());
        }
        log.info("Reply sent with status: " + HttpStatus.OK);
        return ResponseEntity.ok(result);
    }
}
