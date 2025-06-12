package com.project.userRegistration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.userRegistration.resource.GeoLocationResponseResource;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;

@Service
public interface UserService {
    public Optional<GeoLocationResponseResource> getLocation(String ipAddress, UUID randomUuid, @NotBlank String username) throws JsonProcessingException;
}
