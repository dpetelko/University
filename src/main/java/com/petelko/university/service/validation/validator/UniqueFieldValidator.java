package com.petelko.university.service.validation.validator;

import com.petelko.university.service.validation.annotation.UniqueField;
import com.petelko.university.service.validation.validator.uniquevalidator.UniqueValidator;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueFieldValidator.class);
    public static final String ID = "id";
    private String fieldName;
    private String message;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private Map<String, UniqueValidator> commandMap;

    @Override
    public void initialize(UniqueField constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object validatedDTO, ConstraintValidatorContext context) {
        boolean result = false;
        try {
            String fieldValue = getFieldValueAsString(validatedDTO, fieldName);
            Long validatedDTOId = getFieldValueAsLong(validatedDTO, ID);
            UniqueValidator validator = commandMap.get(validatedDTO.getClass().getSimpleName());
            result = validator.isUniqueField(fieldValue, validatedDTOId);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn("Critical error during validation {} for field {} ", validatedDTO.getClass().getSimpleName(), fieldName);
            LOGGER.warn(e.getStackTrace().toString());
        }
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fieldName)
                    .addConstraintViolation();
        }
        return result;
    }

    private String getFieldValueAsString(Object validatedDTO, String fieldName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return BeanUtils.getProperty(validatedDTO, fieldName);
    }

    private Long getFieldValueAsLong(Object validatedDTO, String fieldName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String fieldValue = getFieldValueAsString(validatedDTO, fieldName);
        Long result;
        if (Objects.equals(fieldValue, null)) {
            result = null;
        } else {
            result = Long.parseLong(fieldValue);
        }
        return result;
    }

}
