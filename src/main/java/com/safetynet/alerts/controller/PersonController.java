package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        log.info("POST request received: Adding a person.");
        Person addedPerson = personService.addPerson(person);
        log.info("Person added successfully.");
        return new ResponseEntity<>(addedPerson, HttpStatus.CREATED);
    }

    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody Person updatedPerson) {
        log.info("PUT request received: Updating a person for {} {}.", firstName, lastName);
        Person person = personService.updatePerson(firstName, lastName, updatedPerson);
        if (person != null) {
            log.info("Person updated successfully.");
            return ResponseEntity.ok(person);
        } else {
            log.info("No person found for {} {}.", firstName, lastName);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        log.info("DELETE request received: Deleting a person for {} {}.", firstName, lastName);
        boolean deleted = personService.deletePerson(firstName, lastName);
        if (deleted) {
            log.info("Person deleted successfully.");
            return ResponseEntity.noContent().build();
        } else {
            log.info("No person found for {} {}.", firstName, lastName);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        log.info("GET request received: Getting all persons.");
        List<Person> persons = personService.getAllPersons();
        log.info("Reply sent with status: " + HttpStatus.OK);
        return ResponseEntity.ok(persons);
    }
}
