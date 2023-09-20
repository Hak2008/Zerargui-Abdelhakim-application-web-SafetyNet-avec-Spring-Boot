package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityEmailService {

    private final PersonService personService;

    public List<String> getEmailsByCity(String city) {
        log.debug("Starting email retrieval for city {}", city);

        List<Person> personsInCity = personService.getAllPersons().stream()
                .filter(person -> city.equals(person.getCity()))
                .collect(Collectors.toList());

        List<String> emails = personsInCity.stream()
                .map(Person::getEmail)
                .collect(Collectors.toList());

        log.info("Emails found for city {}: {}",city, emails);

        return emails;
    }
}
