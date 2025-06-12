package com.project.userRegistration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Current {
    private String time;
    private int interval;
    private double temperature_2m;
    private double rain;
    private double showers;
    private double snowfall;
    private double wind_speed_10m;
    private int is_day;
}
