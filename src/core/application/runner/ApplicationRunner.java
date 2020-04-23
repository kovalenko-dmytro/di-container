package core.application.runner;

import core.application.configuration.ArgumentsParser;
import core.application.configuration.console.ConsoleArgumentsParser;
import core.application.exception.ApplicationException;
import core.application.info.ApiInfo;
import core.application.info.console.ConsoleApiInfo;
import core.application.input.RequestParser;
import core.application.input.console.ConsoleRequestParser;
import core.application.input.entity.ConsoleRequest;
import core.application.invoke.Invoker;
import core.application.invoke.console.ConsoleControllerMethodInvoker;
import core.application.resolve.Resolver;
import core.application.resolve.console.ConsoleControllerResolver;
import core.application.resolve.entity.RequestPathMatchResult;
import core.application.runner.constant.RunnerConstant;
import core.application.runner.constant.RunnerType;
import core.application.runner.factory.CommandProvider;
import core.application.runner.factory.CommandProviderFactory;
import core.application.validate.Validator;
import core.application.validate.console.ControllerMethodArgsValidator;
import core.ioc.exception.BeanCreationException;

import java.util.Arrays;
import java.util.Map;

public class ApplicationRunner {

    private ArgumentsParser argumentsParser;
    private RequestParser<ConsoleRequest> requestParser;
    private Resolver<ConsoleRequest, RequestPathMatchResult> resolver;
    private Validator<RequestPathMatchResult, ConsoleRequest> validator;
    private Invoker<RequestPathMatchResult, ConsoleRequest> invoker;
    private ApiInfo apiInfo;

    private ApplicationRunner() {
        argumentsParser = new ConsoleArgumentsParser();
        requestParser = new ConsoleRequestParser();
        resolver = new ConsoleControllerResolver();
        validator = new ControllerMethodArgsValidator();
        invoker = new ConsoleControllerMethodInvoker();
        apiInfo = new ConsoleApiInfo();
    }

    private static class RunnerHolder {
        private static final ApplicationRunner instance = new ApplicationRunner();
    }

    public static ApplicationRunner getInstance() {
        return RunnerHolder.instance;
    }

    public void run(String ... args) throws ApplicationException {
        Map<String, String> programArguments = argumentsParser.parse(args);
        CommandProvider provider = CommandProviderFactory.getProvider(findRunnerType(programArguments));
        String input;
        while (true) {
            input = provider.nextCommand();
            if (checkExit(input)) { break; }
            process(input);
        }
    }

    private String findRunnerType(Map<String, String> programArguments) {
        return programArguments.keySet().stream()
            .filter(key ->
                Arrays.stream(RunnerType.values())
                    .anyMatch(type -> type.getValue().equalsIgnoreCase(key)))
            .findFirst()
            .orElse(null);
    }

    private void process(String input) {
        try {
            if (checkInfo(input)) {
                apiInfo.getInfo();
                return;
            }
            ConsoleRequest request = requestParser.parse(input);
            RequestPathMatchResult pathMatchResult = resolver.resolve(request);
            validator.validate(pathMatchResult, request);
            invoker.invoke(pathMatchResult, request);
        } catch (ApplicationException | BeanCreationException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean checkExit(String input) {
        return RunnerConstant.EXIT_COMMAND_NAME.getValue().equalsIgnoreCase(input);
    }

    private boolean checkInfo(String input) {
        return RunnerConstant.INFO_COMMAND_NAME.getValue().equalsIgnoreCase(input);
    }
}
