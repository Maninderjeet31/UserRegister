package com.project.userRegistration.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.userRegistration.resource.GeoLocationResponseResource;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class UserServiceImplTest {

    private static final String IP_ADDRESS = "24.48.0.9";
    private static final String IP_ADDRESS_INVALID = "24.48.0.9.09";

    @Test
    public void geoLocationResponse_Success() throws JsonProcessingException {
        RestTemplate restTemplate = mock(RestTemplate.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(restTemplate);
        GeoLocationResponseResource response = createGeoResponse(IP_ADDRESS);
        UUID RANDOM_UUID = UUID.randomUUID();

        when(restTemplate.getForObject(anyString(), eq(GeoLocationResponseResource.class)))
                .thenReturn(response);

        Optional<GeoLocationResponseResource> result = userServiceImpl.getLocation(IP_ADDRESS, RANDOM_UUID, anyString());

        assertTrue(result.isPresent());
        assertEquals("success", result.get().getStatus());
        assertEquals("Canada", result.get().getCountry());
    }

    @Test
    public void geoLocationResponse_Fail() throws JsonProcessingException {
        RestTemplate restTemplate = mock(RestTemplate.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl(restTemplate);
        GeoLocationResponseResource response = createInvalidGeoResponse(IP_ADDRESS_INVALID);
        UUID RANDOM_UUID = UUID.randomUUID();

        when(restTemplate.getForObject(anyString(), eq(GeoLocationResponseResource.class)))
                .thenReturn(response);

        Optional<GeoLocationResponseResource> result = userServiceImpl.getLocation(IP_ADDRESS_INVALID, RANDOM_UUID, anyString());

        assertTrue(result.isPresent());
        assertEquals("fail", result.get().getStatus());
    }

    private GeoLocationResponseResource createGeoResponse(String ipAddress) {
        return GeoLocationResponseResource.builder()
                .query("24.48.0.9")
                .status("success")
                .country("Canada")
                .countryCode("CA")
                .city("Montreal")
                .build();
    }

    private GeoLocationResponseResource createInvalidGeoResponse(String ipAddress) {
        return GeoLocationResponseResource.builder()
                .query("24.48.0.9.09")
                .status("fail")
                .build();
    }
}