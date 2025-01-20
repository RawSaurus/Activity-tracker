package com.miroslav.acitivity_tracker.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum BusinessErrorCodes {
    NO_CODE(0, NOT_IMPLEMENTED, "No code"),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "The new password does not match"),
    ACCOUNT_LOCKED(302, FORBIDDEN, "User account is locked"),
    ACCOUNT_DISABLED(303, FORBIDDEN, "User account is disabled"),
    BAD_CREDENTIALS(304, FORBIDDEN, "Login and / or password is incorrect"),
    ITEM_ALREADY_EXISTS(400, BAD_REQUEST, "Item already exists" ),
    ACTION_NOT_ALLOWED(400, BAD_REQUEST, "Action is not allowed"),
    ACCESS_DENIED(401, UNAUTHORIZED, "You do not have permission for this action"),
    ENTITY_NOT_FOUND(404, NOT_FOUND, "entity not found")
    ;

    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpstatus;


    BusinessErrorCodes(int code, HttpStatus httpstatus, String description) {
        this.code = code;
        this.httpstatus = httpstatus;
        this.description = description;
    }
}
