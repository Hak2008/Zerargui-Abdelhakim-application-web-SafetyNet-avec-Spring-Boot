package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final List<Person> persons = new ArrayList<>();

    public Person addPerson(Person person) {
        persons.add(person);
        return person;
    }

    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        return persons.stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst()
                .map(existingPerson -> {
                    existingPerson.setAddress(updatedPerson.getAddress());
                    existingPerson.setCity(updatedPerson.getCity());
                    existingPerson.setZip(updatedPerson.getZip());
                    existingPerson.setPhone(updatedPerson.getPhone());
                    existingPerson.setEmail(updatedPerson.getEmail());
                    return existingPerson;
                })
                .orElse(null);
    }

    public boolean deletePerson(String firstName, String lastName) {
        return persons.removeIf(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName));
    }

    public List<Person> getAllPersons() {
        return persons;
    }
}