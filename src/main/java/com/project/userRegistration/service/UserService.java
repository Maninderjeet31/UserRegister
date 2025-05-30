package com.project.userRegistration.service;

import com.project.userRegistration.resource.GeoLocationResponseResource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    public Optional<GeoLocationResponseResource> getLocation(String ipAddress);
}
