package com.safetynet.alerts.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.service.FireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FireController.class)
public class FireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireService fireService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetResidentsAndFireStationByAddress() throws Exception {
        String address = "21 Main Street";

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> fireStationInfo = new HashMap<>();
        fireStationInfo.put("fireStation", "1");
        result.add(fireStationInfo);
        Map<String, Object> residentInfo = new HashMap<>();
        residentInfo.put("firstName", "Paul");
        residentInfo.put("lastName", "Henri");
        residentInfo.put("phone", "123-123-1234");
        residentInfo.put("age", 40);
        List<String> medications = new ArrayList<>();
        medications.add("noxidian");
        residentInfo.put("medications", medications);
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        residentInfo.put("allergies", allergies);
        result.add(residentInfo);

        when(fireService.getResidentsAndFireStationByAddress(eq(address))).thenReturn(result);

        mockMvc.perform(get("/fire")
                        .param("address", address))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].fireStation").value("1"))
                .andExpect(jsonPath("$[1].firstName").value("Paul"))
                .andExpect(jsonPath("$[1].lastName").value("Henri"))
                .andExpect(jsonPath("$[1].phone").value("123-123-1234"))
                .andExpect(jsonPath("$[1].age").value(40))
                .andExpect(jsonPath("$[1].medications[0]").value("noxidian"))
                .andExpect(jsonPath("$[1].allergies[0]").value("nillacilan"));
    }
}

