package org.tn.subscriptiontool.core.security.payloads.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String token;
    private Map<String, Object> user;
}
