package com.safetynet.alerts;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.FireStationCoverageController;
import com.safetynet.alerts.service.FireStationCoverageService;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FireStationCoverageController.class)
public class FireStationCoverageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationCoverageService fireStationCoverageService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetPeopleCoveredByStation() throws Exception {
        String stationNumber = "1";

        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> persons = List.of(
                Map.of("firstName", "Paul", "lastName", "Henri", "address", "21 Main Street", "phone", "123-123-1234", "age", "30"),
                Map.of("firstName", "Jeanine", "lastName", "Jean", "address", "22 Main Street", "phone", "111-222-3333", "age", "17")
        );
        result.put("persons", persons);
        result.put("numberOfAdults", 1);
        result.put("numberOfChildren", 1);

        when(fireStationCoverageService.getPeopleCoveredByStation(eq(stationNumber))).thenReturn(result);

        mockMvc.perform(get("/firestation/coverage")
                        .param("stationNumber", stationNumber))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.persons[0].firstName").value("Paul"))
                .andExpect(jsonPath("$.persons[0].lastName").value("Henri"))
                .andExpect(jsonPath("$.persons[0].address").value("21 Main Street"))
                .andExpect(jsonPath("$.persons[0].phone").value("123-123-1234"))
                .andExpect(jsonPath("$.persons[0].age").value("30"))
                .andExpect(jsonPath("$.persons[1].firstName").value("Jeanine"))
                .andExpect(jsonPath("$.persons[1].lastName").value("Jean"))
                .andExpect(jsonPath("$.persons[1].address").value("22 Main Street"))
                .andExpect(jsonPath("$.persons[1].phone").value("111-222-3333"))
                .andExpect(jsonPath("$.persons[1].age").value("17"))
                .andExpect(jsonPath("$.numberOfAdults").value(1))
                .andExpect(jsonPath("$.numberOfChildren").value(1));
    }
}
