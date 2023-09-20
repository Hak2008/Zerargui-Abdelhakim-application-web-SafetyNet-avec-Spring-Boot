package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneAlertService {

    private final FireStationService fireStationService;
    private final PersonService personService;

    public List<String> getPhoneAlertByFireStation(String fireStationNumber) {
        log.debug("Getting phone alert for fire station: {}", fireStationNumber);

        List<String> phoneNumbers = new ArrayList<>();

        for (FireStation fireStation : fireStationService.getAllFireStations()) {
            if (fireStation.getStation().equals(fireStationNumber)) {
                String address = fireStation.getAddress();
                for (Person person : personService.getAllPersons()) {
                    if (person.getAddress().equals(address)) {
                        phoneNumbers.add(person.getPhone());
                    }
                }
            }
        }

        log.info("Phone numbers found for fire station {}: {}", fireStationNumber, phoneNumbers);

        return phoneNumbers;
    }
}
