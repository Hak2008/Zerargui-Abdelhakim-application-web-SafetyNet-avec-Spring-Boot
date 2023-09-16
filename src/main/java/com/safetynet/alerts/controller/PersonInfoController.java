package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.PersonInfoService;
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
@RequestMapping("/personInfo")
public class PersonInfoController {

    private final PersonInfoService personInfoService;

    @Autowired
    public PersonInfoController(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getPersonInfoByLastName(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        log.info("GET request received: Get person info by first name {} and last name {}", firstName, lastName);

        List<Map<String, Object>> personInfo = personInfoService.getPersonInfoByLastName(lastName);

        if (personInfo.isEmpty()) {
            log.info("No person info found for first name {} and last name {}", firstName, lastName);
            return ResponseEntity.notFound().build();
        }

        log.info("Reply sent with status: {}", HttpStatus.OK);
        return ResponseEntity.ok(personInfo);
    }
}

