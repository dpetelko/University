package com.petelko.university.service.validation.validator;

import com.petelko.university.controller.dto.LessonFilterDTO;
import com.petelko.university.service.validation.annotation.DatesOrderCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Period;

import static java.util.Objects.isNull;

public class DatesOrderValidator implements ConstraintValidator<DatesOrderCheck, LessonFilterDTO> {

   public static final String END_DATE = "endDate";
   private String message;

   @Override
   public void initialize(DatesOrderCheck constraint) {
      message = constraint.message();
   }

   @Override
   public boolean isValid(LessonFilterDTO reportRequest, ConstraintValidatorContext context) {
      boolean result = true;
      if (!someDateIsNull(reportRequest)) {
         result = validate(reportRequest);
      }
      if (!result) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate(message)
                 .addPropertyNode(END_DATE)
                 .addConstraintViolation();
      }
     return result;
   }

   private boolean validate (LessonFilterDTO reportRequest) {
      return !Period.between(reportRequest.getStartDate(),
              reportRequest.getEndDate()).isNegative();
   }

   private boolean someDateIsNull(LessonFilterDTO reportRequest) {
      return isNull(reportRequest.getStartDate()) || isNull(reportRequest.getEndDate());
   }
}
