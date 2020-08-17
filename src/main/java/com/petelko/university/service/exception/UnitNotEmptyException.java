package com.petelko.university.service.exception;

import org.springframework.lang.Nullable;

public class UnitNotEmptyException extends RuntimeException {
    private static final long serialVersionUID = 5603012820268006400L;

    public UnitNotEmptyException() {
    }

    public UnitNotEmptyException(String message) {
        super(message);
    }

    public UnitNotEmptyException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
