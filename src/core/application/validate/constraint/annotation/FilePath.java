package core.application.validate.constraint.annotation;

import core.application.validate.annotation.Constraint;
import core.application.validate.constraint.validator.FilePathConstraintValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FilePathConstraintValidator.class)
public @interface FilePath {
    String message() default "must be valid file or directory path";
}
