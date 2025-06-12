package com.project.userRegistration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.userRegistration.resource.GeoLocationResponseResource;
import com.project.userRegistration.resource.UserRegistrationInput;
import com.project.userRegistration.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserRegistrationController controller;

    @Mock
    UserService userService;

    private static final String URL = "/register/user/";

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void registerUser_success() throws Exception {
        UserRegistrationInput request = createRegistrationresource();
        GeoLocationResponseResource response = createGeoResponse(anyString());
        UUID RANDOM_UUID = UUID.randomUUID();

        when(userService.getLocation(request.getIpAddress(), RANDOM_UUID, request.getUsername()))
                .thenReturn(Optional.of(response));

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message.status").value("registration success"))
                .andExpect(jsonPath("$.message.name").value("MANINDER"))
                .andExpect(jsonPath("$.message.country").value("Canada"));

    }

    @Test
    public void registerUser_whenOutsideOfCanada_fail() throws Exception {
        UserRegistrationInput request = createRegistrationresource();
        GeoLocationResponseResource response = createGeoResponseOutsideOfCanada(anyString());
        UUID RANDOM_UUID = UUID.randomUUID();

        when(userService.getLocation(request.getIpAddress(), RANDOM_UUID, request.getUsername()))
                .thenReturn(Optional.of(response));

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message.status").value("registration fail"))
                .andExpect(jsonPath("$.message.name").value("Unable to register user outside of Canada"))
                .andExpect(jsonPath("$.message.country").value("United States"));

    }

    @Test
    public void registerUser_whenIpAddressNotFound_fail() throws Exception {
        UserRegistrationInput request = createInvalidIPRegistrationresource();
        GeoLocationResponseResource response = createFailGeoResponse(anyString());
        UUID RANDOM_UUID = UUID.randomUUID();

        when(userService.getLocation(request.getIpAddress(), RANDOM_UUID, request.getUsername()))
                .thenReturn(Optional.of(response));

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message.status").value("registration fail"))
                .andExpect(jsonPath("$.message.name").value("Unable to process the IP address"))
                .andExpect(jsonPath("$.message.country").value("Not Found"));

    }

    @Test
    public void registerUser_whenInvalidInput_fail() throws Exception {
        UserRegistrationInput request = createInvalidInputRegistrationresource();

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
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

    private GeoLocationResponseResource createGeoResponseOutsideOfCanada(String ipAddress) {
        return GeoLocationResponseResource.builder()
                .query("24.48.0.9")
                .status("success")
                .country("United States")
                .countryCode("US")
                .city("Cartersville")
                .build();
    }

    private GeoLocationResponseResource createFailGeoResponse(String ipAddress) {
        return GeoLocationResponseResource.builder()
                .query("24.48.0.9")
                .status("fail")
                .build();
    }

    private UserRegistrationInput createRegistrationresource() {
        return UserRegistrationInput.builder()
                .username("Maninder")
                .password("Maninder123")
                .ipAddress("24.48.0.9")
                .build();
    }

    private UserRegistrationInput createInvalidIPRegistrationresource() {
        return UserRegistrationInput.builder()
                .username("Maninder")
                .password("Maninder123")
                .ipAddress("24.48.0.9.09")
                .build();
    }

    private UserRegistrationInput createInvalidInputRegistrationresource() {
        return UserRegistrationInput.builder()
                .username("")
                .password("mani123")
                .ipAddress("24.48.0.9")
                .build();
    }
}