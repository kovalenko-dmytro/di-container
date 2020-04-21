package core.application.validate.constraint;

import java.lang.annotation.Annotation;

public interface ConstraintValidator<A extends Annotation, P> {
    boolean isValid(P param);
}
