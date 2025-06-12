package com.project.userRegistration.external.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.userRegistration.model.WeatherResponseResource;
import com.project.userRegistration.resource.WeatherApiResponseResource;
import com.project.userRegistration.resource.WeatherRequestResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

//@FeignClient(name = "WEATHER-SERVICE", path = "http://localhost:8082/weather/")
@FeignClient(name = "WEATHER-SERVICE")
public interface WeatherService {

    @PostMapping("/weather/")
    WeatherApiResponseResource getWeather(
            @RequestBody @Valid WeatherRequestResource weatherRequestResource);
}