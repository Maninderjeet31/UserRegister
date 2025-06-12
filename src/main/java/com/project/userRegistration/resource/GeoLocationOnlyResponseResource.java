package com.project.userRegistration.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.userRegistration.model.WeatherResponseResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocationOnlyResponseResource {

    private UUID userUUID;
    private String name;
    private String query;
    private String status;
    private String country;
    private String countryCode;
    private String city;
    private float lat;
    private float lon;
}
