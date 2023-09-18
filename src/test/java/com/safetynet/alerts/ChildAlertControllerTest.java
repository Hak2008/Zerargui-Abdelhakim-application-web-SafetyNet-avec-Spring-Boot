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
import com.safetynet.alerts.controller.ChildAlertController;
import com.safetynet.alerts.service.ChildAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ChildAlertController.class)
public class ChildAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChildAlertService childAlertService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetChildAlertForAddress() throws Exception {
        String address = "21 Main Street";

        List<Map<String, Object>> childAlertList = new ArrayList<>();
        Map<String, Object> childDetails = new HashMap<>();
        childDetails.put("firstName", "Paul");
        childDetails.put("lastName", "Henri");
        childDetails.put("age", 10);
        List<String> otherHouseholdMembers = new ArrayList<>();
        otherHouseholdMembers.add("Tom henri");
        otherHouseholdMembers.add("Jeanne Henri");
        childDetails.put("otherHouseholdMembers", otherHouseholdMembers);
        childAlertList.add(childDetails);

        when(childAlertService.getChildAlertForAddress(eq(address))).thenReturn(childAlertList);

        mockMvc.perform(get("/childAlert")
                        .param("address", address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Paul"))
                .andExpect(jsonPath("$[0].lastName").value("Henri"))
                .andExpect(jsonPath("$[0].age").value(10))
                .andExpect(jsonPath("$[0].otherHouseholdMembers").isArray())
                .andExpect(jsonPath("$[0].otherHouseholdMembers[0]").value("Tom henri"))
                .andExpect(jsonPath("$[0].otherHouseholdMembers[1]").value("Jeanne Henri"));
    }
}

