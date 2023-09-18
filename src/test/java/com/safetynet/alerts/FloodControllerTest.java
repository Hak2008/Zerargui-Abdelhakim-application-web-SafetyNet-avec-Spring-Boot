package com.safetynet.alerts;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.FloodController;
import com.safetynet.alerts.service.FloodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FloodController.class)
public class FloodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FloodService floodService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetHomesServedByStations() throws Exception {
        List<String> stationNumbers = List.of("1", "2");

        List<Map<String, Object>> homes = new ArrayList<>();

        Map<String, Object> home1 = new HashMap<>();
        home1.put("address", "21 Main Street");
        List<Map<String, String>> residents1 = new ArrayList<>();
        residents1.add(Map.of("firstName", "Paul", "lastName", "Henri", "phone", "123-123-1234"));
        residents1.add(Map.of("firstName", "Jeanine", "lastName", "Jean", "phone", "333-333-3333"));
        home1.put("residents", residents1);
        homes.add(home1);

        Map<String, Object> home2 = new HashMap<>();
        home2.put("address", "22 Main Street");
        List<Map<String, String>> residents2 = new ArrayList<>();
        residents2.add(Map.of("firstName", "Tom", "lastName", "Jones", "phone", "444-444-4444"));
        home2.put("residents", residents2);
        homes.add(home2);

        when(floodService.getHomesServedByStations(eq(stationNumbers))).thenReturn(homes);

        mockMvc.perform(get("/flood/stations")
                        .param("stations", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].address").value("21 Main Street"))
                .andExpect(jsonPath("$[0].residents[0].firstName").value("Paul"))
                .andExpect(jsonPath("$[0].residents[0].lastName").value("Henri"))
                .andExpect(jsonPath("$[0].residents[0].phone").value("123-123-1234"))
                .andExpect(jsonPath("$[0].residents[1].firstName").value("Jeanine"))
                .andExpect(jsonPath("$[0].residents[1].lastName").value("Jean"))
                .andExpect(jsonPath("$[0].residents[1].phone").value("333-333-3333"))
                .andExpect(jsonPath("$[1].address").value("22 Main Street"))
                .andExpect(jsonPath("$[1].residents[0].firstName").value("Tom"))
                .andExpect(jsonPath("$[1].residents[0].lastName").value("Jones"))
                .andExpect(jsonPath("$[1].residents[0].phone").value("444-444-4444"));
    }
}


