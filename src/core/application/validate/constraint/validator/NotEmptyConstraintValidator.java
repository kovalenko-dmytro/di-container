package core.application.validate.constraint.validator;

import core.application.validate.constraint.ConstraintValidator;
import core.application.validate.constraint.annotation.NotEmpty;

public class NotEmptyConstraintValidator implements ConstraintValidator<NotEmpty, String> {

    @Override
    public boolean isValid(String param) {
        return !param.isEmpty();
    }
}
