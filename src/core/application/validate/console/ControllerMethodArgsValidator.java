package core.application.validate.console;

import core.application.exception.ApplicationException;
import core.application.input.entity.ConsoleRequest;
import core.application.resolve.annotation.PathVariable;
import core.application.resolve.entity.RequestPathMatchResult;
import core.application.validate.Validator;
import core.application.validate.annotation.Constraint;
import core.application.validate.constraint.annotation.FilePath;
import core.application.validate.constraint.annotation.NotBlank;
import core.application.validate.constraint.annotation.NotEmpty;
import core.application.validate.constraint.annotation.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ControllerMethodArgsValidator implements Validator<RequestPathMatchResult, ConsoleRequest> {

    private static final Class[] VALIDATE_ANNOTATIONS = new Class[]{FilePath.class, NotNull.class, NotEmpty.class, NotBlank.class};

    @Override
    public void validate(RequestPathMatchResult matchResult, ConsoleRequest request) throws ApplicationException {
        for (Parameter parameter: matchResult.getRequestPathMethod().getParameters()) {
            List<Annotation> annotations = Arrays.stream(parameter.getAnnotations())
                .filter(filterByValidateAnnotations())
                .collect(Collectors.toList());
            for (Annotation annotation: annotations) {
                Constraint constraint = annotation.annotationType().getAnnotation(Constraint.class);
                Class constraintValidatorClass = constraint.validatedBy();
                try {
                    Object constraintValidator = constraintValidatorClass.getDeclaredConstructor().newInstance();
                    Method validateMethod = constraintValidator.getClass().getDeclaredMethod("isValid", String.class);
                    if (parameter.isAnnotationPresent(PathVariable.class)) {
                        String name = parameter.getAnnotation(PathVariable.class).name();
                        boolean res = (boolean)validateMethod.invoke(constraintValidator, request.getRequestParameters().get(name));
                        if (!res) {
                            Class aClass1 = Arrays.stream(VALIDATE_ANNOTATIONS).filter(aClass -> aClass.equals(annotation.annotationType())).findFirst().get();
                            Method m = annotation.annotationType().getDeclaredMethod("message");
                            throw new ApplicationException(name + " path variable " + m.invoke(annotation));
                        }
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Predicate<Annotation> filterByValidateAnnotations() {
        return annotation ->
            Arrays.stream(VALIDATE_ANNOTATIONS).anyMatch(va -> va.equals(annotation.annotationType()));
    }
}
