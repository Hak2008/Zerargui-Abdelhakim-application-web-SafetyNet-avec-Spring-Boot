package com.safetynet.alerts;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.PersonInfoController;
import com.safetynet.alerts.service.PersonInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebMvcTest(controllers = PersonInfoController.class)
public class PersonInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonInfoService personInfoService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetPersonInfoByLastName() throws Exception {
        String firstName = "Paul";
        String lastName = "Henri";

        Map<String, Object> personInfo = new HashMap<>();
        personInfo.put("firstName", firstName);
        personInfo.put("lastName", lastName);
        personInfo.put("address", "21 Main Street");
        personInfo.put("email", "paul.henri@email.com");
        personInfo.put("age", 30);
        personInfo.put("medications", "ibupurin");
        personInfo.put("allergies", "nillacilan");

        List<Map<String, Object>> result = new ArrayList<>();
        result.add(personInfo);

        when(personInfoService.getPersonInfoByLastName(eq(lastName))).thenReturn(result);

        mockMvc.perform(get("/personInfo")
                        .param("firstName", firstName)
                        .param("lastName", lastName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value(firstName))
                .andExpect(jsonPath("$[0].lastName").value(lastName))
                .andExpect(jsonPath("$[0].address").value("21 Main Street"))
                .andExpect(jsonPath("$[0].email").value("paul.henri@email.com"))
                .andExpect(jsonPath("$[0].age").value(30))
                .andExpect(jsonPath("$[0].medications").value("ibupurin"))
                .andExpect(jsonPath("$[0].allergies").value("nillacilan"));
    }
}
