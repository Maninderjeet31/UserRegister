package com.project.userRegistration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.userRegistration.resource.*;
import com.project.userRegistration.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/register/user")
@Log4j2
public class UserRegistrationController {

    private static final UUID RANDOM_UUID = UUID.randomUUID();
    private static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Autowired
    UserService userService;

    @PostMapping("/")
    public ResponseEntity<RegistrationResponseResource> registerUser(
            @RequestBody @Valid UserRegistrationInput userRegistrationInput) throws JsonProcessingException {

        //logging request status for IP Address
        log.info("IP Address requested for {}", userRegistrationInput.getIpAddress());

        //Fetching the response from API with IP Address
        Optional<GeoLocationResponseResource> response = userService.getLocation(userRegistrationInput.getIpAddress()
                        , RANDOM_UUID, userRegistrationInput.getUsername());
        log.info("Geo response: {}", response.get().toString());


        if(response.isPresent() && response.get().getStatus().trim().equalsIgnoreCase("success")){

            //if the user is registering from within Canada
            if (response.get().getCountry().trim().equalsIgnoreCase("canada")){

                //log user registration request status
                log.info("User registration successful, location: {}", response.get().getCountry());



                //setting response values
                RegistrationResponseResource apiResponse = getApiResponse(
                        RANDOM_UUID,
                        response.get().getStatus(),
                        userRegistrationInput.getUsername().toUpperCase(),
                        response.get().getCountry(),
                        response.get().getWeatherResponse().getCurrent().getTemperature_2m(),
                        response.get().getWeatherResponse().getCurrent_units().getTemperature_2m());
                return new ResponseEntity<>(apiResponse,HttpStatus.OK);
            }
            //if the user is registering from outside of Canada
            else {

                //log user registration request status
                log.error("User registration unsuccessful, location: {}", response.get().getCountry());

                //setting response values
                RegistrationResponseResource apiResponse = getApiResponse(
                        EMPTY_UUID,
                        "fail",
                        "Unable to register user outside of Canada",
                        response.get().getCountry(),
                        response.get().getWeatherResponse().getCurrent().getTemperature_2m(),
                        response.get().getWeatherResponse().getCurrent_units().getTemperature_2m());
                return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
            }
        }
        //if the API couldn't locate the IP Address's location
        else {

            //logging failed response from API
            log.info("Failed to receive response from API for the IP-Address: {}", userRegistrationInput.getIpAddress());

            //setting response values
            RegistrationResponseResource apiResponse = getApiResponse(
                        EMPTY_UUID,
                        "fail",
                        "Unable to process the IP address",
                        "Not Found",
                        0.0,
                        "°C");
            return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
        }

    }

    //General method - generating the response message
    private static RegistrationResponseResource getApiResponse(UUID emptyUuid,
                                                               String responseStatus,
                                                               String name,
                                                               String countryName,
                                                               double cityTemperature,
                                                               String temperatureUnit) {
        return RegistrationResponseResource.builder()
                .userId(emptyUuid)
                .message(RegistrationStatusMessage.builder()
                        .status("registration " + responseStatus)
                        .name(name)
                        .country(countryName)
                        .cityTemperature(cityTemperature)
                        .temperatureUnit(temperatureUnit)
                        .build())
                .build();
    }
}
