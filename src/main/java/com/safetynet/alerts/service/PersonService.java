package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final List<Person> persons = new ArrayList<>();

    public Person addPerson(Person person) {
        log.debug("Adding person: {}", person.getFirstName() + " " + person.getLastName());
        persons.add(person);
        log.info("Person added successfully: {}", person.getFirstName() + " " + person.getLastName());
        return person;
    }

    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        log.debug("Updating person: {}", firstName + " " + lastName);
        return persons.stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst()
                .map(existingPerson -> {
                    existingPerson.setAddress(updatedPerson.getAddress());
                    existingPerson.setCity(updatedPerson.getCity());
                    existingPerson.setZip(updatedPerson.getZip());
                    existingPerson.setPhone(updatedPerson.getPhone());
                    existingPerson.setEmail(updatedPerson.getEmail());
                    log.info("Person updated successfully: {}", existingPerson.getFirstName() + " " + existingPerson.getLastName());
                    return existingPerson;
                })
                .orElse(null);
    }

    public boolean deletePerson(String firstName, String lastName) {
        log.debug("Deleting person: {}", firstName + " " + lastName);
        boolean result = persons.removeIf(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName));
        if (result) {
            log.info("Person deleted successfully: {}", firstName + " " + lastName);
        } else {
            log.error("Person not found for deletion: {}", firstName + " " + lastName);
        }
        return result;
    }

    public List<Person> getAllPersons() {
        log.debug("Retrieving all persons");
        return persons;
    }
}