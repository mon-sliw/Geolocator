package com.example.geolocator.service;

import com.example.geolocator.entity.Geolocation;
import com.example.geolocator.repository.GeolocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeolocationServiceImpl implements GeolocationService {

    GeolocationRepository repository;

    @Override
    public Geolocation addGeolocation(Geolocation geolocation) {
        return repository.save(geolocation);
    }
}
