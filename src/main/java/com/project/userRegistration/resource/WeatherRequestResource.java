package com.project.userRegistration.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherRequestResource {

    private UUID userUUID;
    private String name;
    private String country;
    private String city;
    private float lat;
    private float lon;
}
