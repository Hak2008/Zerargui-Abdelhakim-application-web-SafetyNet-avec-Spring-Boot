package com.safetynet.alerts.controller;


import com.safetynet.alerts.service.CommunityEmailService;
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

@Slf4j
@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    private final CommunityEmailService communityEmailService;

    @Autowired
    public CommunityEmailController(CommunityEmailService communityEmailService) {

        this.communityEmailService = communityEmailService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getEmailsByCity(@RequestParam("city") String city) {
        log.info("GET request received: Get emails by city {}", city);

        List<String> emails = communityEmailService.getEmailsByCity(city);

        if (emails.isEmpty()) {
            log.info("No emails found for city {}", city);
            return ResponseEntity.ok(Collections.emptyList());
        }
        log.info("Reply sent with status: " + HttpStatus.OK);
        return ResponseEntity.ok(emails);
    }
}

