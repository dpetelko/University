package com.petelko.university.repository.exception;

import org.springframework.lang.Nullable;


public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5603012820268006494L;

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
