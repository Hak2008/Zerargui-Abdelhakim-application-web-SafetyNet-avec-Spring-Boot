package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddMedicalRecord() throws Exception {
        MedicalRecord medicalRecordToAdd = new MedicalRecord();
        medicalRecordToAdd.setFirstName("Paul");
        medicalRecordToAdd.setLastName("Henri");

        when(medicalRecordService.addMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecordToAdd);

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicalRecordToAdd)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Paul"))
                .andExpect(jsonPath("$.lastName").value("Henri"));
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception {

        MedicalRecord updatedMedicalRecord = new MedicalRecord();
        updatedMedicalRecord.setBirthdate("01/01/1990");
        updatedMedicalRecord.setMedications(Arrays.asList("aspirin", "ibuprofen"));
        updatedMedicalRecord.setAllergies(Arrays.asList("peanut"));

        when(medicalRecordService.updateMedicalRecord(eq("Paul"), eq("Henri"), any(MedicalRecord.class)))
                .thenReturn(updatedMedicalRecord);

        mockMvc.perform(put("/medicalRecord/Paul/Henri")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedMedicalRecord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthdate").value("01/01/1990"))
                .andExpect(jsonPath("$.medications", hasSize(2)))
                .andExpect(jsonPath("$.allergies", hasSize(1)));
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        when(medicalRecordService.deleteMedicalRecord(eq("Paul"), eq("Henri"))).thenReturn(true);

        mockMvc.perform(delete("/medicalRecord/Paul/Henri"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllMedicalRecords() throws Exception {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord());
        medicalRecords.add(new MedicalRecord());

        when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecords);

        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
