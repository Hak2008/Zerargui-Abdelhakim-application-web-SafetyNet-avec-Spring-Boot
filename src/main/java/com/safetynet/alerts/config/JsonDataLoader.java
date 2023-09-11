package com.safetynet.alerts.config;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class JsonDataLoader implements CommandLineRunner {

    private final PersonService personService;
    private final FireStationService fireStationService;
    private final MedicalRecordService medicalRecordService;

    @Autowired
    public JsonDataLoader(
            PersonService personService,
            FireStationService fireStationService,
            MedicalRecordService medicalRecordService
    ) {
        this.personService = personService;
        this.fireStationService = fireStationService;
        this.medicalRecordService = medicalRecordService;
    }

    @Override
    public void run(String... args) throws IOException {
        String jsonUrl = "https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json";
        URL url = new URL(jsonUrl);

        try (InputStream inputStream = url.openStream()) {

            String jsonContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            Any data = JsonIterator.deserialize(jsonContent);

            List<Any> personsData = data.get("persons").asList();
            List<Any> fireStationsData = data.get("firestations").asList();
            List<Any> medicalRecordsData = data.get("medicalrecords").asList();

            for (Any personData : personsData) {
                Person person = personData.as(Person.class);
                personService.addPerson(person);
            }
            for (Any fireStationData : fireStationsData) {
                FireStation fireStation = fireStationData.as(FireStation.class);
                fireStationService.addFireStation(fireStation);
            }
            for (Any medicalRecordData : medicalRecordsData) {
                MedicalRecord medicalRecord = medicalRecordData.as(MedicalRecord.class);
                medicalRecordService.addMedicalRecord(medicalRecord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

