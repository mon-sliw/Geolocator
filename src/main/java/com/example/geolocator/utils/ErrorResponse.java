package com.example.geolocator.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
public class ErrorResponse {

    @Getter
    private ArrayList<String> errors = new ArrayList<>();

    ErrorResponse(String message) {
        errors.add(message);
    }

    void add(String message) {
        errors.add(message);
    }
}
