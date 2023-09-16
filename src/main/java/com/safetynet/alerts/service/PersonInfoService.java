package com.safetynet.alerts.service;


import com.safetynet.alerts.model.MedicalRecord;
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
public class PersonInfoService {

    private final PersonService personService;

    public List<Map<String, Object>> getPersonInfoByLastName(String lastName) {

        List<Person> allPersons = personService.getAllPersons();
        Map<String, List<Map<String, Object>>> resultByLastName = new HashMap<>();

        List<Person> matchingPersons = allPersons.stream()
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());

        for (Person person : matchingPersons) {
            Map<String, Object> personInfo = new HashMap<>();
            personInfo.put("firstName", person.getFirstName());
            personInfo.put("lastName", person.getLastName());
            personInfo.put("address", person.getAddress());
            personInfo.put("email", person.getEmail());

            MedicalRecord medicalRecord = person.getMedicalRecord();
            if (medicalRecord != null) {
                personInfo.put("age", medicalRecord.getAge());
                personInfo.put("medications", medicalRecord.getMedications());
                personInfo.put("allergies", medicalRecord.getAllergies());
            }

            resultByLastName.computeIfAbsent(lastName, k -> new ArrayList<>()).add(personInfo);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        resultByLastName.forEach((lastNameKey, persons) -> {
            Map<String, Object> lastNameInfo = new HashMap<>();
            lastNameInfo.put("lastName", lastNameKey);
            lastNameInfo.put("persons", persons);
            result.add(lastNameInfo);
        });
        return result;
    }
}

