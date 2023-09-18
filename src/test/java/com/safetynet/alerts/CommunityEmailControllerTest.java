package com.safetynet.alerts;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.CommunityEmailController;
import com.safetynet.alerts.service.CommunityEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CommunityEmailController.class)
public class CommunityEmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommunityEmailService communityEmailService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetEmailsByCity() throws Exception {
        String city = "Paris";

        List<String> emails = new ArrayList<>();
        emails.add("paul@email.com");
        emails.add("tom@email.com");

        when(communityEmailService.getEmailsByCity(eq(city))).thenReturn(emails);

        mockMvc.perform(get("/communityEmail")
                        .param("city", city))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("paul@email.com"))
                .andExpect(jsonPath("$[1]").value("tom@email.com"));
    }
}
