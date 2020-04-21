package core.application.validate.console;

import core.application.exception.ApplicationException;
import core.application.input.entity.ConsoleRequest;
import core.application.resolve.annotation.PathVariable;
import core.application.resolve.entity.RequestPathMatchResult;
import core.application.validate.Validator;
import core.application.validate.annotation.Constraint;
import core.application.validate.annotation.Messaged;
import core.application.validate.constraint.ConstraintValidator;
import core.application.validate.constraint.annotation.FilePath;
import core.application.validate.constraint.annotation.NotBlank;
import core.application.validate.constraint.annotation.NotEmpty;
import core.application.validate.constraint.annotation.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerMethodArgsValidator implements Validator<RequestPathMatchResult, ConsoleRequest> {

    private static final Class[] VALIDATE_ANNOTATIONS = new Class[]{FilePath.class, NotNull.class, NotEmpty.class, NotBlank.class};

    @Override
    public void validate(RequestPathMatchResult matchResult, ConsoleRequest request) throws ApplicationException {
        for (Parameter parameter: matchResult.getRequestPathMethod().getParameters()) {
            for (Annotation annotation: filterValidateAnnotations(parameter)) {
                processConstraintValidator(request, parameter, annotation);
            }
        }
    }

    private void processConstraintValidator(ConsoleRequest request, Parameter parameter, Annotation annotation) throws ApplicationException {
        ConstraintValidator constraintValidator = initConstraintValidator(annotation);
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            String parameterName = parameter.getAnnotation(PathVariable.class).name();
            boolean result = constraintValidator.isValid(request.getRequestParameters().get(parameterName));
            if (!result) {
                handleValidateResult(annotation, parameterName);
            }
        }
    }

    private ConstraintValidator initConstraintValidator(Annotation annotation) throws ApplicationException {
        Constraint constraint = annotation.annotationType().getAnnotation(Constraint.class);
        Class constraintValidatorClass = constraint.validatedBy();
        try {
            return (ConstraintValidator) constraintValidatorClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ApplicationException("");
        }
    }

    private void handleValidateResult(Annotation annotation, String parameterName) throws ApplicationException {
        String message = annotation.annotationType().getAnnotation(Messaged.class).message();
        throw new ApplicationException(parameterName + " path variable " + message);
    }

    private List<Annotation> filterValidateAnnotations(Parameter parameter) {
        return Arrays.stream(parameter.getAnnotations())
            .filter(annotation ->
                Arrays.stream(VALIDATE_ANNOTATIONS).anyMatch(va -> va.equals(annotation.annotationType())))
            .collect(Collectors.toList());
    }
}
