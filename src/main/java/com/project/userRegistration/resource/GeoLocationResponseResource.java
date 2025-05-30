package com.project.userRegistration.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocationResponseResource {

    private String query;
    private String status;
    private String country;
    private String countryCode;
    private String city;

}
