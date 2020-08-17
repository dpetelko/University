package com.petelko.university.controller.rest;

import com.petelko.university.controller.rest.dto.ValidationError;
import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.repository.exception.QueryNotExecuteException;
import com.petelko.university.service.exception.InvalidEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)

public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    public static final String LINE_MASK = "Oops ... Something went wrong%s!";
    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        LOG.warn("{} - '{}'", e.getClass().getSimpleName(), e.getBindingResult());
        List<ValidationError> body = e.getBindingResult().getFieldErrors().stream()
                .map(x -> new ValidationError(x.getField(), x.getDefaultMessage()))
                .collect(toList());
        return new ResponseEntity<>(body, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        LOG.error("{} - '{}'", e.getClass().getSimpleName(), e.getMessage());
        String msg = "";
        HttpStatus status = INTERNAL_SERVER_ERROR;
        if (e instanceof QueryNotExecuteException) {
            msg = String.format(LINE_MASK, " with database");
        } else if (e instanceof EntityNotFoundException) {
            msg = e.getMessage();
            status = NOT_FOUND;
        } else if (e instanceof InvalidEntityException) {
            msg = e.getMessage();
            status = BAD_REQUEST;
        } else {
            msg = e.getMessage();
        }
        return new ResponseEntity<>(msg, status);
    }
}
