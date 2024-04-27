package com.prodyna.reserveyourspot.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RYSValidationException extends RuntimeException {
    private ErrorCode errorCode;

    public RYSValidationException(ErrorCode errorCode, String message) {
        super(message);
        this.setErrorCode(errorCode);
    }
}
