package com.globallogic.vehicle.registry.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.vehicle.registry.exceptions.RegistryException;
import com.globallogic.vehicle.registry.exceptions.RegistryResourceNotFound;
import com.globallogic.vehicle.registry.service.RegistryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(RegistryController.class)
class RegistryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistryService service;

    @Test
    void givenValidVin_whenGet_thenReturnVehicleJson() throws Exception {
        VehicleSO vehicle = new VehicleSO(1, "vinTest", 2022, "TST", "Test");

        when(service.get("vinTest")).thenReturn(vehicle);

        mockMvc.perform(get("/vehicles/{vin}", "vinTest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productionYear", Matchers.is(2022)))
                .andExpect(jsonPath("$.brand", Matchers.is("TST")))
                .andExpect(jsonPath("$.model", Matchers.is("Test")))
                .andExpect(jsonPath("$.vin", Matchers.is("vinTest")))
                .andExpect(jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void givenInvalidVin_whenGet_thenExpectNotFound() throws Exception {
        when(service.get("invalidVin"))
                .thenThrow(new RegistryResourceNotFound("Vehicle with given VIN does not exist."));

        mockMvc.perform(get("/vehicles/{vin}", "invalidVin"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error",Matchers.is("Vehicle with given VIN does not exist.")));
    }

    @Test
    void givenContent_whenGetAll_thenReturnJsonArray() throws Exception {
        VehicleSO vehicle = new VehicleSO(1, "vinTest", 2022, "TST", "Test");

        when(service.getAll()).thenReturn(List.of(vehicle));

        mockMvc.perform(get("/vehicles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].productionYear", Matchers.is(2022)))
                .andExpect(jsonPath("$[0].brand", Matchers.is("TST")))
                .andExpect(jsonPath("$[0].model", Matchers.is("Test")))
                .andExpect(jsonPath("$[0].vin", Matchers.is("vinTest")))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)));

    }

    @Test
    void givenNoContent_whenGetAll_thenReturnJsonArray() throws Exception {
        when(service.getAll()).thenThrow(new RegistryResourceNotFound("No vehicles found"));

        mockMvc.perform(get("/vehicles"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error",Matchers.is("No vehicles found")));

    }

    @Test
    void givenVehicle_whenCreate_thenCreateVehicle() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        VehicleSO vehicle = new VehicleSO(1, "vinTest", 2022, "TST", "Test");

        when(service.create(vehicle)).thenReturn(vehicle);

        mockMvc.perform(post("/vehicles")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(vehicle)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productionYear", Matchers.is(2022)))
                .andExpect(jsonPath("$.brand", Matchers.is("TST")))
                .andExpect(jsonPath("$.model", Matchers.is("Test")))
                .andExpect(jsonPath("$.vin", Matchers.is("vinTest")))
                .andExpect(jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void whenDelete_thenDelete() throws Exception {
        mockMvc.perform(delete("/vehicles/{}", "vinTest"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}