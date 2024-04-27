package com.prodyna.reserveyourspot.exception;

import io.swagger.annotations.ApiModel;
import org.springframework.http.HttpStatus;

@ApiModel("ErrorCode")
public enum ErrorCode {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "Invalid request"),
    DB_CONNECTION_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "Could not connect to database server"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected general error"),
    RESOURCE_NOT_FOUND(HttpStatus.BAD_REQUEST, "Resource not found"),
    ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "Property %s must not be blank"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "No permission to this resource"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Authorization failed");

    private HttpStatus status;
    private String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
