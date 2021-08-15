package com.example.geolocator.service;

import com.example.geolocator.entity.Geolocation;
import com.example.geolocator.repository.GeolocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeolocationServiceImplTest {

    private GeolocationServiceImpl geolocationService;

    @Mock
    private GeolocationRepository repository;

    @BeforeEach
    void setUp() {
        geolocationService = new GeolocationServiceImpl(repository);
    }

    @Test
    void addedGeolocation() {

        when(repository.save(any(Geolocation.class))).then(returnsFirstArg());

        Geolocation geolocation = Geolocation.builder()
                .deviceId("abc")
                .latitude(123456.)
                .longitude(1234567.)
                .build();

        Geolocation addedGeolocation = geolocationService.addGeolocation(geolocation);

        assertAll("add geolocation",
                () -> assertNotNull(addedGeolocation),
                () -> assertEquals(addedGeolocation.getDeviceId(), geolocation.getDeviceId()),
                () -> assertEquals(addedGeolocation.getLatitude(), geolocation.getLatitude()),
                () -> assertEquals(addedGeolocation.getLongitude(), geolocation.getLongitude()));
    }
}