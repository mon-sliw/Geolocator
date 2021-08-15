package com.example.geolocator.controller;

import com.example.geolocator.entity.Geolocation;
import com.example.geolocator.service.GeolocationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/geolocation")
public class GeolocationController {

    GeolocationService service;

    @PostMapping("/add")
    public ResponseEntity<?> addGeolocation(@Valid @RequestBody Geolocation geolocation) {
        Geolocation addedGeolocation = service.addGeolocation(geolocation);
        if (addedGeolocation == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("new location for deviceId: {} at latitude: {} and longitude: {}", addedGeolocation.getDeviceId(), addedGeolocation.getLatitude(), addedGeolocation.getLongitude());
        return ResponseEntity.ok(addedGeolocation);
    }
}
