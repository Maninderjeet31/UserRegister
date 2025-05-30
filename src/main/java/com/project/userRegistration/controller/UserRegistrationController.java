package com.project.userRegistration.controller;

import com.project.userRegistration.resource.GeoLocationResponseResource;
import com.project.userRegistration.resource.RegistrationResponseResource;
import com.project.userRegistration.resource.RegistrationStatusMessage;
import com.project.userRegistration.resource.UserRegistrationInput;
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

    private static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Autowired
    UserService userService;

    @PostMapping("/")
    public ResponseEntity<RegistrationResponseResource> registerUser(
            @RequestBody @Valid UserRegistrationInput userRegistrationInput){

        //logging request status for IP Address
        log.info("IP Address requested for {}", userRegistrationInput.getIpAddress());

        //Fetching the response from API with IP Address
        Optional<GeoLocationResponseResource> response = userService.getLocation(userRegistrationInput.getIpAddress());

        if(response.isPresent() && response.get().getStatus().trim().equalsIgnoreCase("success")){

            //if the user is registering from within Canada
            if (response.get().getCountry().trim().equalsIgnoreCase("canada")){

                //log user registration request status
                log.info("User registration successful, location: {}", response.get().getCountry());

                //setting response values
                RegistrationResponseResource apiResponse = getApiResponse(
                        UUID.randomUUID(),
                        response.get().getStatus(),
                        userRegistrationInput.getUsername().toUpperCase(),
                        response.get().getCountry());
                return new ResponseEntity<>(apiResponse,HttpStatus.OK);
            }
            //if the user is registering from outside of Canada
            else {

                //log user registration request status
                log.info("User registration unsuccessful, location: {}", response.get().getCountry());

                //setting response values
                RegistrationResponseResource apiResponse = getApiResponse(
                        EMPTY_UUID,
                        "fail",
                        "Unable to register user outside of Canada",
                        response.get().getCountry());
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
                        "Not Found");
            return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
        }

    }

    //General method - generating the response message
    private static RegistrationResponseResource getApiResponse(UUID emptyUuid, String responseStatus, String name, String countryName) {
        return RegistrationResponseResource.builder()
                .userId(emptyUuid)
                .message(RegistrationStatusMessage.builder()
                        .status("registration " + responseStatus)
                        .name(name)
                        .country(countryName)
                        .build())
                .build();
    }
}
