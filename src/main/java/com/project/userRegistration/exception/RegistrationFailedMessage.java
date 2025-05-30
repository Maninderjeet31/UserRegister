package com.project.userRegistration.exception;

import com.project.userRegistration.resource.RegistrationStatusMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RegistrationFailedMessage {

    private String message;
    private int status;
    private long timeStamp;

}
