package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FireStationCoverageService {

    private final PersonService personService;
    private final FireStationService fireStationService;

    public Map<String, Object> getPeopleCoveredByStation(String stationNumber) {
        log.info("GET request received: Get people covered by fire station {}", stationNumber);

        List<FireStation> fireStations = fireStationService.getAllFireStations();

        List<String> coveredAddresses = fireStations.stream()
                .filter(fireStation -> fireStation.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        List<Map<String, String>> peopleCoveredByStation = personService.getAllPersons().stream()
                .filter(person -> coveredAddresses.contains(person.getAddress()))
                .map(this::mapPersonToMap)
                .collect(Collectors.toList());

        int numberOfAdults = (int) peopleCoveredByStation.stream()
                .filter(person -> person.get("age") != null && Integer.parseInt(person.get("age")) > 18)
                .count();

        int numberOfChildren = (int) peopleCoveredByStation.stream()
                .filter(person -> person.get("age") != null && Integer.parseInt(person.get("age")) <= 18)
                .count();

        log.debug("Fire station coverage processing completed for station {}", stationNumber);

        Map<String, Object> result = new HashMap<>();
        result.put("persons", peopleCoveredByStation);
        result.put("numberOfAdults", numberOfAdults);
        result.put("numberOfChildren", numberOfChildren);

        log.info("Reply sent with successful status");
        return result;
    }

    private Map<String, String> mapPersonToMap(Person person) {
        Map<String, String> personInfo = new HashMap<>();
        personInfo.put("firstName", person.getFirstName());
        personInfo.put("lastName", person.getLastName());
        personInfo.put("address", person.getAddress());
        personInfo.put("phone", person.getPhone());

        MedicalRecord medicalRecord = person.getMedicalRecord();
        if (medicalRecord != null) {
            int age = medicalRecord.getAge();
            personInfo.put("age", String.valueOf(age));
        }
        return personInfo;
    }
}
