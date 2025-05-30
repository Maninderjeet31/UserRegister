package com.project.userRegistration.service.impl;

import com.project.userRegistration.resource.GeoLocationresponseResource;
import com.project.userRegistration.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Override
    public Optional<GeoLocationresponseResource> getLocation(String ipAddress) {

        String query = "http://ip-api.com/json/" + ipAddress + "?fields=status,message,country,countryCode,city,query";
        RestTemplate restTemplate = new RestTemplate();
        Optional<GeoLocationresponseResource> response = Optional.ofNullable(restTemplate.getForObject(query, GeoLocationresponseResource.class));

        return response;
    }
}
