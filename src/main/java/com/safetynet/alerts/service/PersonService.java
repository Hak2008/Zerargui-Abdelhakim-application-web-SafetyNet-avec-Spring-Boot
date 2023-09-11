package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person addPerson(Person person) {

        return personRepository.save(person);
    }
    public Optional<Person> findByFirstNameAndLastName(String firstName, String lastName) {
        return personRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Person updatePerson(String firstName, String lastName, Person updatedPerson) {
        Optional<Person> optionalPerson = personRepository.findByFirstNameAndLastName(firstName, lastName);

        if (optionalPerson.isPresent()) {
            Person existingPerson = optionalPerson.get();
            existingPerson.setAddress(updatedPerson.getAddress());
            existingPerson.setCity(updatedPerson.getCity());
            existingPerson.setZip(updatedPerson.getZip());
            existingPerson.setPhone(updatedPerson.getPhone());
            existingPerson.setEmail(updatedPerson.getEmail());
            return personRepository.save(existingPerson);
        } else {
            return null;
        }
    }

    public boolean deletePerson(String firstName, String lastName) {
        Optional<Person> optionalPerson = personRepository.findByFirstNameAndLastName(firstName, lastName);

        if (optionalPerson.isPresent()) {
            personRepository.delete(optionalPerson.get());
            return true;
        } else {
            return false;
        }
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
