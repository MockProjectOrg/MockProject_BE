package org.example.bookingbe.custom.annotationValidate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AgeOver18Validator implements ConstraintValidator<AgeOver18, LocalDate> {
    @Override
    public boolean isValid(LocalDate birthday, ConstraintValidatorContext constraintValidatorContext) {
        if (birthday == null) {
            return false;
        }
        return Period.between(birthday, LocalDate.now()).getYears() >= 18;
    }
}
