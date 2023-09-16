package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChildAlertService {

    private final PersonService personService;

    public List<Map<String, Object>> getChildAlertForAddress(String address) {
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
        }
        return childAlertList;
    }
}
