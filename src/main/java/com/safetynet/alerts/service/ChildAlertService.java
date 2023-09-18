package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChildAlertService {

    private final PersonService personService;

    public List<Map<String, Object>> getChildAlertForAddress(String address) {
        log.info("GET request received: Get child alert for address {}", address);
        log.debug("Starting child alert processing for address {}", address);
        List<Person> allPersons = personService.getAllPersons();
        List<Map<String, Object>> childAlertList = new ArrayList<>();

        List<Person> children = allPersons.stream()
                .filter(person -> person.getAddress().equals(address) && person.getMedicalRecord() != null
                        && person.getMedicalRecord().getAge() <= 18)
                .collect(Collectors.toList());

        if (!children.isEmpty()) {
            for (Person child : children) {
                Map<String, Object> childDetails = new HashMap<>();
                childDetails.put("firstName", child.getFirstName());
                childDetails.put("lastName", child.getLastName());
                childDetails.put("age", child.getMedicalRecord().getAge());

                List<String> otherHouseholdMembers = allPersons.stream()
                        .filter(person -> person.getAddress().equals(address) && !person.equals(child))
                        .map(person -> person.getFirstName() + " " + person.getLastName())
                        .collect(Collectors.toList());

                childDetails.put("otherHouseholdMembers", otherHouseholdMembers);
                childAlertList.add(childDetails);
            }
        }else {
            log.info("No children found at address {}", address);
        }
        log.debug("Child alert processing completed for address {}", address);
        log.info("Reply sent with status: {}", HttpStatus.OK);
        return childAlertList;
    }
}
