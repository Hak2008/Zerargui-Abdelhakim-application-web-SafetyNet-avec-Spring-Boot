package com.safetynet.alerts.service;


import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityEmailService {

    private final PersonService personService;

    public List<String> getEmailsByCity(String city) {
        List<Person> personsInCity = personService.getAllPersons().stream()
                .filter(person -> city.equals(person.getCity()))
                .collect(Collectors.toList());

        return personsInCity.stream()
                .map(Person::getEmail)
                .collect(Collectors.toList());
    }
}
