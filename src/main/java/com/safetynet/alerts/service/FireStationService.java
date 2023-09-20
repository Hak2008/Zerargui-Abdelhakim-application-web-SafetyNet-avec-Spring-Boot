package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FireStationService {

    private final List<FireStation> fireStations = new ArrayList<>();

    public FireStation addFireStation(FireStation fireStation) {
        fireStations.add(fireStation);
        log.info("Fire station added: {}", fireStation);
        return (fireStation);
    }

    public FireStation updateFireStation(String address, FireStation updatedFireStation) {
        Optional<FireStation> optionalFireStation = fireStations.stream()
                .filter(fireStation -> fireStation.getAddress().equals(address))
                .findFirst();

        if (optionalFireStation.isPresent()) {
            FireStation existingFireStation = optionalFireStation.get();
            existingFireStation.setStation(updatedFireStation.getStation());
            log.info("Fire station updated: {}", existingFireStation);
            return existingFireStation;
        } else {
            log.error("Fire station not found for address: {}", address);
            return null;
        }
    }

    public boolean deleteFireStation(String address) {
        boolean removed = fireStations.removeIf(fireStation -> fireStation.getAddress().equals(address));
        if (removed) {
            log.info("Fire station deleted for address: {}", address);
        } else {
            log.error("Fire station not found for address: {}", address);
        }
        return removed;
    }

    public List<FireStation> getAllFireStations() {
        return fireStations;
    }
}
