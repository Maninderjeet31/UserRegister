package com.project.userRegistration.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.userRegistration.external.service.WeatherService;
import com.project.userRegistration.model.WeatherResponseResource;
import com.project.userRegistration.resource.GeoLocationOnlyResponseResource;
import com.project.userRegistration.resource.GeoLocationResponseResource;
import com.project.userRegistration.resource.WeatherApiResponseResource;
import com.project.userRegistration.resource.WeatherRequestResource;
import com.project.userRegistration.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    WeatherService weatherService;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Direct call via RestTemplate
    /* @Override
    public Optional<GeoLocationResponseResource> getLocation(String ipAddress, UUID randomUuid, @NotBlank String username) throws JsonProcessingException {

        String query = "http://ip-api.com/json/" + ipAddress + "?fields=status,message,country,countryCode,city,lat,lon,query";

        ResponseEntity<GeoLocationOnlyResponseResource> jsonResponse = restTemplate.getForEntity(query, GeoLocationOnlyResponseResource.class);
        log.info("The message received via rest template directly {}", jsonResponse.getBody());

        WeatherRequestResource weatherRequestResource = WeatherRequestResource.builder()
                                                        .userUUID(randomUuid)
                                                        .name(username.toUpperCase())
                                                        .country(Objects.requireNonNull(jsonResponse.getBody()).getCountry())
                                                        .city(jsonResponse.getBody().getCity())
                                                        .lat(jsonResponse.getBody().getLat())
                                                        .lon(jsonResponse.getBody().getLon())
                                                        .build();

        String jsonResponseString = objectMapper.writeValueAsString(weatherRequestResource);
        log.info("The message received via rest template directly as String-->>> : {}", jsonResponseString);

        //Calling method from Weather Service
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonResponseString, headers);

        String weatherMicroserviceUrl = "http://localhost:8082/weather/";
        ResponseEntity<WeatherApiResponseResource> jsonWeatherResponse =  restTemplate.postForEntity(weatherMicroserviceUrl, request, WeatherApiResponseResource.class);
        log.info("The message received via WEATHER API SERVICE directly-->>> : {}", jsonWeatherResponse);

        GeoLocationResponseResource finalJsonResponse = GeoLocationResponseResource.builder()
                .query(jsonResponse.getBody().getQuery())
                .status(jsonResponse.getBody().getStatus())
                .country(jsonResponse.getBody().getCountry())
                .countryCode(jsonResponse.getBody().getCountryCode())
                .city(jsonResponse.getBody().getCity())
                .lat(jsonResponse.getBody().getLat())
                .lon(jsonResponse.getBody().getLon())
                .weatherResponse(Objects.requireNonNull(jsonWeatherResponse.getBody()).getMessage())
                .build();

        log.info("The final response after weather api call {}", jsonResponse);

        return Optional.of(finalJsonResponse);
    }

     */

    //Calling other microservice via Feign Client
    @Override
    public Optional<GeoLocationResponseResource> getLocation(String ipAddress, UUID randomUuid, @NotBlank String username) throws JsonProcessingException {

        String query = "http://ip-api.com/json/" + ipAddress + "?fields=status,message,country,countryCode,city,lat,lon,query";

        ResponseEntity<GeoLocationOnlyResponseResource> jsonResponse = restTemplate.getForEntity(query, GeoLocationOnlyResponseResource.class);
        log.info("The message received via rest template directly {}", jsonResponse.getBody());

        WeatherRequestResource weatherRequestResource = WeatherRequestResource.builder()
                .userUUID(randomUuid)
                .name(username.toUpperCase())
                .country(Objects.requireNonNull(jsonResponse.getBody()).getCountry())
                .city(jsonResponse.getBody().getCity())
                .lat(jsonResponse.getBody().getLat())
                .lon(jsonResponse.getBody().getLon())
                .build();

        WeatherApiResponseResource jsonWeatherResponse =  weatherService.getWeather(weatherRequestResource);
        log.info("The message received via WEATHER API SERVICE directly-->>> : {}", jsonWeatherResponse);

        GeoLocationResponseResource finalJsonResponse = GeoLocationResponseResource.builder()
                .query(jsonResponse.getBody().getQuery())
                .status(jsonResponse.getBody().getStatus())
                .country(jsonResponse.getBody().getCountry())
                .countryCode(jsonResponse.getBody().getCountryCode())
                .city(jsonResponse.getBody().getCity())
                .lat(jsonResponse.getBody().getLat())
                .lon(jsonResponse.getBody().getLon())
                .weatherResponse(jsonWeatherResponse.getMessage())
                .build();

        log.info("The final response after weather api call {}", jsonResponse);

        return Optional.of(finalJsonResponse);
    }
}
