package org.tn.subscriptiontool.core.security.services;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tn.subscriptiontool.core.security.authentication.providers.EmailTokenAuthenticationProvider;
import org.tn.subscriptiontool.core.security.authentication.tokens.EmailAuthenticationToken;
import org.tn.subscriptiontool.core.security.models.EmailTemplateName;
import org.tn.subscriptiontool.core.security.models.Token;
import org.tn.subscriptiontool.core.security.models.User;
import org.tn.subscriptiontool.core.security.repository.RoleRepository;
import org.tn.subscriptiontool.core.security.repository.TokenRepository;
import org.tn.subscriptiontool.core.security.repository.UserRepository;
import org.tn.subscriptiontool.core.security.payloads.requests.ActivationRequest;
import org.tn.subscriptiontool.core.security.payloads.requests.AuthenticationRequest;
import org.tn.subscriptiontool.core.security.payloads.requests.RegistrationRequest;
import org.tn.subscriptiontool.core.security.payloads.responses.AuthenticationResponse;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final EmailService emailService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final EmailTokenAuthenticationProvider emailTokenAuthenticationProvider;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    public ResponseEntity<?> register(RegistrationRequest request) throws MessagingException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email Address already in use.");
        }

        var userRole = roleRepository
                .findByName("USER")
                .orElseThrow(() -> new IllegalStateException("RULE USER was not initialized"));

        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);
        sendValidationEmail(user);

        return ResponseEntity.accepted().body(
                AuthenticationResponse
                        .builder()
                        .token(null)
                        .user(userResponseBuilder(user))
                        .build()
        );
    }

    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        User user = (User) userDetails;
        String token = jwtService.generateToken(Map.of("firstName", user.getName()), user);

        return ResponseEntity.accepted().body(AuthenticationResponse
                .builder()
                .token(token)
                .user(userResponseBuilder(user))
                .build());
    }

    public ResponseEntity<?> activateAccount(ActivationRequest request) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new BadCredentialsException("The token no longer exists."));

        if (savedToken.getUser().isEnabled()) {
            throw new IllegalArgumentException("User already enabled!");
        }

        if (savedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            tokenRepository.deleteAllByUser(savedToken.getUser());
            sendValidationEmail(savedToken.getUser());
            throw new BadCredentialsException("Token expired, sending another one to the associated email address.");
        } else {
            var auth = emailTokenAuthenticationProvider.authenticate(
                    new EmailAuthenticationToken(savedToken.getUser().getEmail(), request.getToken())
            );

            savedToken.setValidatedAt(LocalDateTime.now());
            savedToken.getUser().setEnabled(true);
            userRepository.save(savedToken.getUser());
            return ResponseEntity.accepted().body(
                    AuthenticationResponse
                            .builder()
                            .token(auth.getCredentials().toString())
                            .user(userResponseBuilder(savedToken.getUser()))
                            .build()
            );
        }
    }

    private Map<String, Object> userResponseBuilder(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Map<String, Object> userMap = new HashMap<>();

        userMap.put("firstName", user.getFirstName());
        userMap.put("lastName", user.getLastName());
        userMap.put("email", user.getEmail());
        userMap.put("enabled", user.isEnabled());

        return Collections.unmodifiableMap(userMap);
    }

    public void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                newToken,
                "Account Activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationToken(6);
        logger.error("Generated token: " + generatedToken);
        var token = Token
                .builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .user(user)
                .build();

        logger.error("Attemting to save token");
        tokenRepository.save(token);
        logger.error("Token saved");

        return generatedToken;
    }

    private String generateActivationToken(int len) {
        String characters = "0123456789";
        StringBuilder tokenBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(characters.length());
            tokenBuilder.append(characters.charAt(randomIndex));
        }

        return tokenBuilder.toString();
    }
}
