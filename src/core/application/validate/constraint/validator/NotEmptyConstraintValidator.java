package core.application.validate.constraint.validator;

import core.application.validate.constraint.ConstraintValidator;

public class NotEmptyConstraintValidator implements ConstraintValidator {

    @Override
    public boolean isValid(Object param) {
        return !((String)param).isEmpty();
    }
}
