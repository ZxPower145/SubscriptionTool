package org.tn.subscriptiontool.core.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.tn.subscriptiontool.core.security.services.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        if (email != null) {
            String token = jwtService.generateToken(email);

            // Add token to response
            response.setContentType("application/json");
            response.getWriter().write("{\"token\": \"" + token + "\"}");

            // Optionally, set authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else {
            response.sendRedirect("/account/login-failure");
        }
    }
}
