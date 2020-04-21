package core.application.validate.constraint.validator;

import core.application.validate.constraint.ConstraintValidator;
import core.application.validate.constraint.annotation.NotNull;

import java.util.Objects;

public class NotNullConstraintValidator implements ConstraintValidator<NotNull, String> {

    @Override
    public boolean isValid(String param) {
        return !Objects.isNull(param);
    }
}
