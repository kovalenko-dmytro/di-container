package core.application.runner.console;

import core.application.configuration.ArgumentsParser;
import core.application.configuration.console.ConsoleArgumentsParser;
import core.application.exception.ApplicationException;
import core.application.info.ApiInfo;
import core.application.info.console.ConsoleApiInfo;
import core.application.input.RequestParser;
import core.application.input.RequestReader;
import core.application.input.console.ConsoleRequestParser;
import core.application.input.console.ConsoleRequestReader;
import core.application.input.entity.ConsoleRequest;
import core.application.invoke.Invoker;
import core.application.invoke.console.ConsoleControllerMethodInvoker;
import core.application.resolve.Resolver;
import core.application.resolve.console.ConsoleControllerResolver;
import core.application.resolve.entity.RequestPathMatchResult;
import core.application.runner.Runner;
import core.application.runner.constant.RunnerConstant;
import core.application.validate.Validator;
import core.application.validate.console.ControllerMethodArgsValidator;
import core.ioc.exception.BeanCreationException;

public class ConsoleRunner implements Runner {

    private ArgumentsParser argumentsParser;
    private RequestReader<String> reader;
    private RequestParser<ConsoleRequest> parser;
    private Resolver<ConsoleRequest, RequestPathMatchResult> resolver;
    private Validator<RequestPathMatchResult, ConsoleRequest> validator;
    private Invoker<RequestPathMatchResult, ConsoleRequest> invoker;
    private ApiInfo apiInfo;

    public ConsoleRunner() {
        argumentsParser = new ConsoleArgumentsParser();
        reader = new ConsoleRequestReader();
        parser = new ConsoleRequestParser();
        resolver = new ConsoleControllerResolver();
        validator = new ControllerMethodArgsValidator();
        invoker = new ConsoleControllerMethodInvoker();
        apiInfo = new ConsoleApiInfo();
    }

    @Override
    public void run(String ... args) throws ApplicationException {
        printPreview();
        argumentsParser.parse(args);
        String input;
        while (true) {
            input = reader.read();
            if (checkExit(input)) { break; }
            process(input);
        }
    }

    private void printPreview() {
        System.out.println(RunnerConstant.PLEASE_ENTER_REQUEST_COMMAND.getValue());
        System.out.println(RunnerConstant.INFO_TO_VIEW_AVAILABLE_API_INFO.getValue());
        System.out.println(RunnerConstant.EXIT_TO_EXIT_FROM_PROGRAM.getValue());
    }

    private void process(String input) {
        try {
            if (checkInfo(input)) {
                apiInfo.getInfo();
                return;
            }
            ConsoleRequest request = parser.parse(input);
            RequestPathMatchResult pathMatchResult = resolver.resolve(request);
            validator.validate(pathMatchResult, request);
            invoker.invoke(pathMatchResult, request);
        } catch (ApplicationException | BeanCreationException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean checkInfo(String input) {
        return RunnerConstant.INFO_COMMAND_NAME.getValue().equalsIgnoreCase(input);
    }

    private boolean checkExit(String input) {
        return RunnerConstant.EXIT_COMMAND_NAME.getValue().equalsIgnoreCase(input);
    }
}
