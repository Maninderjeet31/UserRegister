package com.project.userRegistration.service.impl;

import com.project.userRegistration.resource.GeoLocationResponseResource;
import com.project.userRegistration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<GeoLocationResponseResource> getLocation(String ipAddress) {

        String query = "http://ip-api.com/json/" + ipAddress + "?fields=status,message,country,countryCode,city,query";
        Optional<GeoLocationResponseResource> response = Optional.ofNullable(restTemplate.getForObject(query, GeoLocationResponseResource.class));

        return response;
    }
}
