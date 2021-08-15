package com.example.geolocator.controller;

import com.example.geolocator.entity.Geolocation;
import com.example.geolocator.service.GeolocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = GeolocationController.class)
class GeolocationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GeolocationService service;

    @BeforeEach
    void setUp() {
        when(service.addGeolocation(any(Geolocation.class))).then(returnsFirstArg());
    }

    @Test
    void whenValidInput_thenReturns200() throws Exception {

        String bodyString = "{\"deviceId\":\"12345\", " +
                "\"latitude\": 505430, " +
                "\"longitude\": 1423412}";

        mvc.perform(post("/geolocation/add")
                .content(bodyString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.deviceId").isNotEmpty())
                .andExpect(jsonPath("$.latitude").isNotEmpty())
                .andExpect(jsonPath("$.longitude").isNotEmpty());
    }

    @Test
    void whenInputInvalid_thenReturns400() throws Exception {

        String bodyString = "{\"deviceId\":\"12345\", " +
                "\"longitude\": 1423412}";

        mvc.perform(post("/geolocation/add")
                .content(bodyString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    void whenDeviceIdMissing_thenReturnsErrorMessage() throws Exception {

        String bodyString = "{\"latitude\": 505430, " +
                "\"longitude\": 1423412}";

        mvc.perform(post("/geolocation/add")
                .content(bodyString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[0]").value("deviceId is mandatory"));
    }

    @Test
    void whenLatitudeMissing_thenReturnsErrorMessage() throws Exception {

        String bodyString = "{\"deviceId\":\"12345\", " +
                "\"longitude\": 1423412}";

        mvc.perform(post("/geolocation/add")
                .content(bodyString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[0]").value("latitude is mandatory"));
    }

    @Test
    void whenLongitudeMissing_thenReturnsErrorMessage() throws Exception {

        String bodyString = "{\"deviceId\":\"12345\", " +
                "\"latitude\": 505430}";

        mvc.perform(post("/geolocation/add")
                .content(bodyString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[0]").value("longitude is mandatory"));
    }

    @Test
    void whenLatitudeTooSmall_thenReturnsErrorMessage() throws Exception {

        String bodyString = "{\"deviceId\":\"12345\", " +
                "\"latitude\": -5054300, " +
                "\"longitude\": 1423412}";

        mvc.perform(post("/geolocation/add")
                .content(bodyString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[0]").value("latitude out of bounds"));
    }

    @Test
    void whenLatitudeTooBig_thenReturnsErrorMessage() throws Exception {

        String bodyString = "{\"deviceId\":\"12345\", " +
                "\"latitude\": 5054300, " +
                "\"longitude\": 1423412}";

        mvc.perform(post("/geolocation/add")
                .content(bodyString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[0]").value("latitude out of bounds"));
    }

    @Test
    void whenLongitudeTooSmall_thenReturnsErrorMessage() throws Exception {

        String bodyString = "{\"deviceId\":\"12345\", " +
                "\"latitude\": 505430, " +
                "\"longitude\": -14234120}";

        mvc.perform(post("/geolocation/add")
                .content(bodyString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[0]").value("longitude out of bounds"));
    }

    @Test
    void whenLongitudeTooBig_thenReturnsErrorMessage() throws Exception {

        String bodyString = "{\"deviceId\":\"12345\", " +
                "\"latitude\": 505430, " +
                "\"longitude\": 14234120}";

        mvc.perform(post("/geolocation/add")
                .content(bodyString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[0]").value("longitude out of bounds"));
    }

    @Test
    void whenMultipleInputInvalidations_thenReturnsMultipleErrorMessages() throws Exception {

        String bodyString = "{\"deviceId\":\"12345\", " +
                "\"latitude\": 5054300, " +
                "\"longitude\": 14234120}";

        mvc.perform(post("/geolocation/add")
                .content(bodyString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors.length()").value(2));
    }
}
