package core.application.validate.constraint.validator;

import core.application.validate.constraint.ConstraintValidator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathConstraintValidator implements ConstraintValidator {

    @Override
    public boolean isValid(Object param) {
        Path path = Paths.get((String) param);
        return Files.isDirectory(path) || Files.isRegularFile(path);
    }
}
