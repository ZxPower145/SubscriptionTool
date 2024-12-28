package org.tn.subscriptiontool.core.security.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/login-success")
    public ResponseEntity<String> loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated user");
        }
        return ResponseEntity.ok("Welcome, " + principal.getAttribute("email"));
    }

    @GetMapping("/login-failure")
    public ResponseEntity<String> loginFailure() {
        return ResponseEntity.badRequest().body("Login failed");
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activate(@RequestBody @Valid ActivationRequest request) throws MessagingException {
        return authenticationService.activateAccount(request);
    }

    @GetMapping("/token/test")
    public ResponseEntity<String> testToken() {
        return ResponseEntity.ok().body("Authenticated");
    }
}
