package com.petelko.university.repository.exception;

import org.springframework.lang.Nullable;

public class QueryNotExecuteException extends RuntimeException {

    private static final long serialVersionUID = 6686414458974299357L;

    public QueryNotExecuteException() {
    }

    public QueryNotExecuteException(String message) {
        super(message);
    }

    public QueryNotExecuteException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
