package com.project.userRegistration.resource;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegistrationStatusMessage {
    private String status;
    private String name;
    private String country;
    private double cityTemperature;
    private String temperatureUnit;
}

