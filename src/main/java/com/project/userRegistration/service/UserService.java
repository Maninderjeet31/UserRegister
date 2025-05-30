package com.project.userRegistration.service;

import com.project.userRegistration.resource.GeoLocationresponseResource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    public Optional<GeoLocationresponseResource> getLocation(String ipAddress);
}
