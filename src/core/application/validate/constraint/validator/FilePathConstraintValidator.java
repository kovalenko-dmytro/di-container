package core.application.validate.constraint.validator;

import core.application.validate.constraint.ConstraintValidator;
import core.application.validate.constraint.annotation.FilePath;

public class FilePathConstraintValidator implements ConstraintValidator<FilePath, String> {

    @Override
    public boolean isValid(String param) {
        return false;
    }
}
