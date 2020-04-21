package core.application.validate.constraint.validator;

import core.application.validate.constraint.ConstraintValidator;
import core.application.validate.constraint.annotation.NotBlank;

public class NotBlankConstraintValidator implements ConstraintValidator<NotBlank, String> {

    @Override
    public boolean isValid(String param) {
        return false;
    }
}
