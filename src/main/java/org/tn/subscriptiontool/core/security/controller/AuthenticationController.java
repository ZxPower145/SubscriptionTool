package org.tn.subscriptiontool.core.security.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tn.subscriptiontool.core.security.payloads.requests.ActivationRequest;
import org.tn.subscriptiontool.core.security.payloads.requests.AuthenticationRequest;
import org.tn.subscriptiontool.core.security.payloads.requests.RegistrationRequest;
import org.tn.subscriptiontool.core.security.services.AuthenticationService;


@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for handling authentication and account-related actions.")
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest
    ) {
        return authenticationService.authenticate(authenticationRequest);
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activate(@RequestBody @Valid ActivationRequest request) throws MessagingException {
        return authenticationService.activateAccount(request);
    }
}
