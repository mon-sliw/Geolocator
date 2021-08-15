package com.example.geolocator.repository;

import com.example.geolocator.entity.Geolocation;
import org.springframework.data.repository.CrudRepository;

public interface GeolocationRepository extends CrudRepository<Geolocation, Long> {
}
