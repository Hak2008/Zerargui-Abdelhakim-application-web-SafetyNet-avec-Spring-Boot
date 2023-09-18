package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FireService {

    private final FireStationService fireStationService;
    private final PersonService personService;

    public List<Map<String, Object>> getResidentsAndFireStationByAddress(String address) {

        List<FireStation> fireStations = fireStationService.getAllFireStations();
        List<Person> allPersons = personService.getAllPersons();

        List<Map<String, Object>> result = new ArrayList<>();
        String fireStationNumber = null;

        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(address)) {
                fireStationNumber = fireStation.getStation();
                break;
            }
        }

        if (fireStationNumber != null) {
            Map<String, Object> fireStationInfo = new HashMap<>();
            fireStationInfo.put("fireStation", fireStationNumber);
            result.add(fireStationInfo);
        }

        for (Person person : allPersons) {
            if (person.getAddress().equals(address)) {
                Map<String, Object> residentInfo = new HashMap<>();
                residentInfo.put("firstName", person.getFirstName());
                residentInfo.put("lastName", person.getLastName());
                residentInfo.put("phone", person.getPhone());

                MedicalRecord medicalRecord = person.getMedicalRecord();
                if (medicalRecord != null) {
                    residentInfo.put("age", medicalRecord.getAge());
                    residentInfo.put("medications", medicalRecord.getMedications());
                    residentInfo.put("allergies", medicalRecord.getAllergies());
                }
                result.add(residentInfo);
            }
        }
        return result;
    }
}