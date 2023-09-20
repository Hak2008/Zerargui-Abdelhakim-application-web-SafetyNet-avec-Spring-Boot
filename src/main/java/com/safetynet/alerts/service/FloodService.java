package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
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
public class FloodService {

    private final PersonService personService;
    private final FireStationService fireStationService;

    public List<Map<String, Object>> getHomesServedByStations(List<String> stationNumbers) {
        log.info("GET request received: Get homes served by stations for station numbers {}", stationNumbers);
        log.debug("Starting flood service processing for station numbers {}", stationNumbers);

        List<Person> allPersons = personService.getAllPersons();
        List<FireStation> allFireStations = fireStationService.getAllFireStations();
        Map<String, List<Map<String, Object>>> resultByAddress = new HashMap<>();

        for (String stationNumber : stationNumbers) {
            List<FireStation> filteredFireStations = allFireStations.stream()
                    .filter(fireStation -> fireStation.getStation().equals(stationNumber))
                    .collect(Collectors.toList());

            for (FireStation fireStation : filteredFireStations) {
                String address = fireStation.getAddress();
                List<Person> residents = allPersons.stream()
                        .filter(person -> person.getAddress().equals(address))
                        .collect(Collectors.toList());

                for (Person resident : residents) {
                    Map<String, Object> homeInfo = new HashMap<>();
                    homeInfo.put("firstName", resident.getFirstName());
                    homeInfo.put("lastName", resident.getLastName());
                    homeInfo.put("phone", resident.getPhone());

                    MedicalRecord medicalRecord = resident.getMedicalRecord();
                    if (medicalRecord != null) {
                        homeInfo.put("age", medicalRecord.getAge());
                        homeInfo.put("medications", medicalRecord.getMedications());
                        homeInfo.put("allergies", medicalRecord.getAllergies());
                    }
                    resultByAddress.computeIfAbsent(address, k -> new ArrayList<>()).add(homeInfo);
                }
            }
        }
        List<Map<String, Object>> result = new ArrayList<>();
        resultByAddress.forEach((address, residents) -> {
            Map<String, Object> addressInfo = new HashMap<>();
            addressInfo.put("address", address);
            addressInfo.put("residents", residents);
            result.add(addressInfo);
        });

        log.debug("Flood service processing completed for station numbers {}", stationNumbers);
        log.info("Reply sent with status: success");

        return result;
    }
}
