package com.project.userRegistration.model;

import com.project.userRegistration.model.Current;
import com.project.userRegistration.model.CurrentUnits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponseResource {

    private float lat;
    private float lon;
    private String timezone;
    private String timezone_abbreviation;
    private CurrentUnits current_units;
    private Current current;
}
