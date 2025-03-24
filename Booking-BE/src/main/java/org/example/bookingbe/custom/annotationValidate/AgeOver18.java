package org.example.bookingbe.custom.annotationValidate;

import jakarta.validation.Constraint;
import org.springframework.messaging.handler.annotation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeOver18Validator.class)
public @interface AgeOver18 {
    String message() default "Age must be over 18";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
