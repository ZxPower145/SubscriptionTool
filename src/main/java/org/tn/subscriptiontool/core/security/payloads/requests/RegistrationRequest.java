package org.tn.subscriptiontool.core.security.payloads.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "Please provide a First Name")
    @NotBlank(message = "Please provide a First Name")
    private String firstName;

    @NotEmpty(message = "Please provide a Last Name")
    @NotBlank(message = "Please provide a Last Name")
    private String lastName;

    @NotEmpty(message = "Please provide an email address")
    @NotBlank(message = "Please provide an email address")
    @Email(message = "Provide an email of type email@example.something")
    private String email;

    @NotEmpty(message = "Please provide a password")
    @NotBlank(message = "Please provide a password")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;
}

