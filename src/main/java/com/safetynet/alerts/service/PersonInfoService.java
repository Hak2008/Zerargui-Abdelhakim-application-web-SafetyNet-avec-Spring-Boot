package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonInfoService {

    private final PersonService personService;

    public List<Map<String, Object>> getPersonInfoByLastName(String lastName) {
        log.debug("Retrieving person information for last name: {}", lastName);
        List<Person> allPersons = personService.getAllPersons();
        Map<String, List<Map<String, Object>>> resultByLastName = new HashMap<>();

        for (Person person : allPersons) {
            if (person.getLastName().equalsIgnoreCase(lastName)) {
                String personLastName = person.getLastName();
                Map<String, Object> personInfo = new HashMap<>();
                personInfo.put("firstName", person.getFirstName());
                personInfo.put("lastName", person.getLastName());
                personInfo.put("address", person.getAddress());
                personInfo.put("email", person.getEmail());

                MedicalRecord medicalRecord = person.getMedicalRecord();
                if (medicalRecord != null) {
                    personInfo.put("birthdate", medicalRecord.getBirthdate());
                    personInfo.put("age", medicalRecord.getAge());
                    personInfo.put("medications", medicalRecord.getMedications());
                    personInfo.put("allergies", medicalRecord.getAllergies());
                }

                resultByLastName.computeIfAbsent(personLastName, k -> new ArrayList<>()).add(personInfo);
            }
        }

        List<Map<String, Object>> result = new ArrayList<>();
        resultByLastName.forEach((lastNameKey, persons) -> {
            Map<String, Object> lastNameInfo = new HashMap<>();
            lastNameInfo.put("lastName", lastNameKey);
            lastNameInfo.put("persons", persons);
            result.add(lastNameInfo);
        });

        log.info("Person information retrieved successfully for last name: {}", lastName);
        return result;
    }
}

