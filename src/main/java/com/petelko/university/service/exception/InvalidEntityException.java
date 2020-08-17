package com.petelko.university.service.exception;

import org.springframework.lang.Nullable;

public class InvalidEntityException extends RuntimeException {

    private static final long serialVersionUID = 5603012820268006469L;

    public InvalidEntityException() {
    }

    public InvalidEntityException(String message) {
        super(message);
    }

    public InvalidEntityException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
