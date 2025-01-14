package org.tn.subscriptiontool.core.security.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ErrorCodes {
    NO_CODE(0, NOT_IMPLEMENTED, "Functionality not implemented."),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Current password is incorrect!"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "New password does not match!"),
    ACCOUNT_DISABLED(303, FORBIDDEN, "Account is disabled."),
    BAD_CREDENTIALS(304, FORBIDDEN, "User email and password does not match!"),
    ACCOUNT_LOCKED(423, LOCKED, "Account is locked."),
    ;

    @Getter
    private final int code;
    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final String description;

    ErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
