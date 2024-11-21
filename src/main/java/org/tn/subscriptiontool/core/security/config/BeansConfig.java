package org.tn.subscriptiontool.core.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tn.subscriptiontool.core.security.authentication.providers.EmailTokenAuthenticationProvider;
import org.tn.subscriptiontool.core.security.repository.UserRepository;
import org.tn.subscriptiontool.core.security.services.JwtService;

import java.util.Arrays;

/**
 * The {@code BeansConfig} class configures Spring Security beans for
 * authentication and password encoding.
 *
 * <p>This configuration class sets up the authentication provider,
 * password encoder, and authentication manager necessary for user
 * authentication processes within the application.</p>
 */
@Configuration
@RequiredArgsConstructor
public class BeansConfig {
    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public EmailTokenAuthenticationProvider emailTokenAuthenticationProvider(
            JwtService jwtService,
            UserRepository userRepository
    ) {
        return new EmailTokenAuthenticationProvider(jwtService, userRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationProvider daoAuthenticationProvider,
            EmailTokenAuthenticationProvider emailTokenAuthenticationProvider
    ) { return new ProviderManager(Arrays.asList(daoAuthenticationProvider, emailTokenAuthenticationProvider)); }
}
