package com.safetynet.alerts;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.FireStationController;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddFireStation() throws Exception {
        FireStation fireStationToAdd = new FireStation();
        fireStationToAdd.setAddress("21 Main Street");
        fireStationToAdd.setStation("1");

        when(fireStationService.addFireStation(any(FireStation.class))).thenReturn(fireStationToAdd);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fireStationToAdd)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address", is("21 Main Street")))
                .andExpect(jsonPath("$.station", is("1")));
    }

    @Test
    public void testUpdateFireStation() throws Exception {
        FireStation updatedFireStation = new FireStation();
        updatedFireStation.setAddress("123 Main St");
        updatedFireStation.setStation("2");

        when(fireStationService.updateFireStation(eq("21 Main Street"), any(FireStation.class))).thenReturn(updatedFireStation);

        mockMvc.perform(put("/firestation/21 Main Street")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFireStation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address", is("123 Main St")))
                .andExpect(jsonPath("$.station", is("2")));
    }

    @Test
    public void testDeleteFireStation() throws Exception {
        when(fireStationService.deleteFireStation(eq("21 Main Street"))).thenReturn(true);

        mockMvc.perform(delete("/firestation/21 Main Street"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllFireStations() throws Exception {
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation());
        fireStations.add(new FireStation());

        when(fireStationService.getAllFireStations()).thenReturn(fireStations);

        mockMvc.perform(get("/firestation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }
}
