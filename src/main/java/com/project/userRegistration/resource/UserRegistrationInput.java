package com.project.userRegistration.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRegistrationInput {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must have at least 1 number and 1 Capital letter")
    @Size(min = 8, message = "Password must be 8 chars long")
    private String password;

    @NotBlank(message = "IP address must be provided.")
    private String ipAddress;

}
