package com.safetynet.alerts.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddPerson() throws Exception {
        Person personToAdd = new Person();
        personToAdd.setFirstName("Paul");
        personToAdd.setLastName("Henri");

        when(personService.addPerson(any(Person.class))).thenReturn(personToAdd);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personToAdd)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Paul")))
                .andExpect(jsonPath("$.lastName", is("Henri")));
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Person updatedPerson = new Person();
        updatedPerson.setAddress("20 Main Street");
        updatedPerson.setPhone("111-111-1111");

        when(personService.updatePerson(eq("Paul"), eq("Henri"), any(Person.class))).thenReturn(updatedPerson);

        mockMvc.perform(put("/person/Paul/Henri")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address", is("20 Main Street")))
                .andExpect(jsonPath("$.phone", is("111-111-1111")));
    }

    @Test
    public void testDeletePerson() throws Exception {
        when(personService.deletePerson(eq("Paul"), eq("Henri"))).thenReturn(true);

        mockMvc.perform(delete("/person/Paul/Henri"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllPersons() throws Exception {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person());
        persons.add(new Person());

        when(personService.getAllPersons()).thenReturn(persons);

        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
