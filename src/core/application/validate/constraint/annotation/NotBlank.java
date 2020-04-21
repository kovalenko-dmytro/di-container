package core.application.validate.constraint.annotation;

import core.application.validate.annotation.Constraint;
import core.application.validate.constraint.validator.NotBlankConstraintValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotBlankConstraintValidator.class)
public @interface NotBlank {
    String message() default "cannot be blank";
}
