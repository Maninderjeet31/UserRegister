package com.project.userRegistration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUnits {
    private String time;
    private String interval;
    private String temperature_2m;
    private String rain;
    private String showers;
    private String snowfall;
    private String wind_speed_10m;
    private String is_day;
}

