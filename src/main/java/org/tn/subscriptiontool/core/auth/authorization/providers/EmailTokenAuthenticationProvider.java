package org.tn.subscriptiontool.core.auth.authorization.providers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.tn.subscriptiontool.core.auth.authorization.tokens.EmailAuthenticationToken;
import org.tn.subscriptiontool.core.auth.models.User;
import org.tn.subscriptiontool.core.auth.repository.TokenRepository;
import org.tn.subscriptiontool.core.auth.repository.UserRepository;
import org.tn.subscriptiontool.core.auth.services.EmailService;
import org.tn.subscriptiontool.core.auth.services.JwtService;

import java.util.Map;


@Component
@RequiredArgsConstructor
public class EmailTokenAuthenticationProvider implements AuthenticationProvider {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailAuthenticationToken auth = (EmailAuthenticationToken) authentication;
        String email = auth.getPrincipal().toString();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or token"));

        user.setEnabled(true);
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(Map.of("fullName", user.getName()), user);

        return new EmailAuthenticationToken(user, jwtToken, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
