package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FireStationService {

    private final List<FireStation> fireStations = new ArrayList<>();

    public FireStation addFireStation(FireStation fireStation) {
        fireStations.add(fireStation);
        return (fireStation);
    }

    public FireStation updateFireStation (String address, FireStation updatedFireStation) {
        return fireStations.stream()
                .filter(fireStation -> fireStation.getAddress().equals(address))
                .findFirst()
                .map(existingFireStation -> {
                    existingFireStation.setStation(updatedFireStation.getStation());
                    return existingFireStation;
                })
                .orElse(null);
    }

    public boolean deleteFireStation ( String address){
        return fireStations.removeIf(fireStation -> fireStation.getAddress().equals(address));
    }

    public List<FireStation> getAllFireStations() {
        return fireStations;
    }
}
