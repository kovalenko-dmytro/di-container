package core.application.validate.annotation;

import core.application.validate.constraint.ConstraintValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraint {
    Class<? extends ConstraintValidator> validatedBy();
}
