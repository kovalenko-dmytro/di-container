package core.application.validate.constraint.validator;

import core.application.validate.constraint.ConstraintValidator;
import core.application.validate.constraint.annotation.FilePath;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathConstraintValidator implements ConstraintValidator<FilePath, String> {

    @Override
    public boolean isValid(String param) {
        Path path = Paths.get(param);
        return Files.isDirectory(path) || Files.isRegularFile(path);
    }
}
