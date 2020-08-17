package com.petelko.university.controller;

import com.petelko.university.repository.exception.EntityNotFoundException;
import com.petelko.university.repository.exception.QueryNotExecuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

    public static final String LINE_MASK = "Oops ... Something went wrong%s!";
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        LOGGER.error("{} - '{}'", e.getClass().getSimpleName(), e.getMessage());
        String msg = "";
        if (e instanceof QueryNotExecuteException) {
            msg = String.format(LINE_MASK, " with database");
        } else if (e instanceof EntityNotFoundException) {
            msg = String.format(LINE_MASK, "! Record not found!");
        } else {
            msg = String.format(LINE_MASK, " with application");
        }
        ModelAndView model = new ModelAndView("/global-error");
        model.addObject("errorMessage", msg);
        return model;
    }
}
