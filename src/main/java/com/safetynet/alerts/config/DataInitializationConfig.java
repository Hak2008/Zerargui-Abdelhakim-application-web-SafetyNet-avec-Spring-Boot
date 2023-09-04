package com.safetynet.alerts.config;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.AppData;
import com.safetynet.alerts.repository.DataFetcher;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class DataInitializationConfig {

    private final DataFetcher dataFetcher;
    private final PersonService personService;
    private final FireStationService fireStationService;
    private final MedicalRecordService medicalRecordService;

    @Autowired
    public DataInitializationConfig(DataFetcher dataFetcher, PersonService personService, FireStationService fireStationService, MedicalRecordService medicalRecordService) {
        this.dataFetcher = dataFetcher;
        this.personService = personService;
        this.fireStationService = fireStationService;
        this.medicalRecordService = medicalRecordService;
    }

    @Bean
    public void initializeData() {
        AppData appData = dataFetcher.fetchDataFromUrl();
        for (Person person : appData.getPersons()) {
            personService.addPerson(person);
        }
        for (FireStation fireStation : appData.getFirestations()) {
            fireStationService.addFireStation(fireStation);
        }
        for (MedicalRecord medicalRecord : appData.getMedicalrecords()) {
            medicalRecordService.addMedicalRecord(medicalRecord);
        }
    }
}

