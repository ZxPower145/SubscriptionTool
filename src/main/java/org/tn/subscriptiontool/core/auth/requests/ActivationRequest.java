package org.tn.subscriptiontool.core.auth.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivationRequest {
    @NotEmpty(message = "Please provide the verification code in order to activate your account.")
    @NotBlank(message = "Please provide the verification code in order to activate your account.")
    private String token;
}
