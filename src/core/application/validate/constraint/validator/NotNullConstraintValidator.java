package core.application.validate.constraint.validator;

import core.application.validate.constraint.ConstraintValidator;

import java.util.Objects;

public class NotNullConstraintValidator implements ConstraintValidator {

    @Override
    public boolean isValid(Object param) {
        return !Objects.isNull(param);
    }
}
