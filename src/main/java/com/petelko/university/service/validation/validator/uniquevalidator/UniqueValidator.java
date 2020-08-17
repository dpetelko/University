package com.petelko.university.service.validation.validator.uniquevalidator;

public interface UniqueValidator {
    boolean isUniqueField(String fieldValue, Long id);
}
