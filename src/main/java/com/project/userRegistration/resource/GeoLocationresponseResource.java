package com.project.userRegistration.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocationresponseResource {

    private String query;
    private String status;
    private String country;
    private String countryCode;
    private String city;

}
