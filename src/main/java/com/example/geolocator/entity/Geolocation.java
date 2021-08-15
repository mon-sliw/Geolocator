package com.example.geolocator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Geolocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "deviceId is mandatory")
    private String deviceId;

    @NotNull(message = "latitude is mandatory")
    @Min(value = -900000, message = "latitude out of bounds")
    @Max(value = 900000, message = "latitude out of bounds")
    private Double latitude;

    @NotNull(message = "longitude is mandatory")
    @Min(value = -1800000, message = "longitude out of bounds")
    @Max(value = 1800000, message = "longitude out of bounds")
    private Double longitude;
}
