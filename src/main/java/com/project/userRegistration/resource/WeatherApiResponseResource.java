package com.project.userRegistration.resource;

import com.project.userRegistration.model.WeatherResponseResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiResponseResource {
    private boolean found;
    private WeatherResponseResource message;
}
