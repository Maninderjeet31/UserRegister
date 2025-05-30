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
public class RegistrationResponseResource {
    private UUID userId;
    private RegistrationStatusMessage message;
}
