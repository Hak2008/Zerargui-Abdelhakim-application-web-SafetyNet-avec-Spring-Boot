package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;

    public FireStation addFireStation(FireStation fireStation) {

        return fireStationRepository.save(fireStation);
    }

    public FireStation updateFireStation(String address, FireStation updatedFireStation) {
        Optional<FireStation> optionalFireStation = fireStationRepository.findByAddress(address);

        if (optionalFireStation.isPresent()) {
            FireStation existingFireStation = optionalFireStation.get();
            existingFireStation.setStation(updatedFireStation.getStation());
            return fireStationRepository.save(existingFireStation);
        } else {
            return null;
        }
    }

    public boolean deleteFireStation(String address) {
        Optional<FireStation> optionalFireStation = fireStationRepository.findByAddress(address);

        if (optionalFireStation.isPresent()) {
            fireStationRepository.delete(optionalFireStation.get());
            return true;
        } else {
            return false;
        }
    }

    public List<FireStation> getAllFireStations() {

        return fireStationRepository.findAll();
    }

    public List<Person> getPersonsByStationNumber(int stationNumber) {
        List<FireStation> fireStations = fireStationRepository.findByStation(Integer.toString(stationNumber));

        List<String> coveredAddresses = fireStations.stream()
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        return personRepository.findByAddressIn(coveredAddresses);
    }
}
