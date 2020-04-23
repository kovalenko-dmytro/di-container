package core.application.runner.factory.console;

import core.application.input.RequestReader;
import core.application.input.console.ConsoleRequestReader;
import core.application.runner.constant.RunnerConstant;
import core.application.runner.factory.CommandProvider;

public class ConsoleCommandProvider implements CommandProvider {

    private RequestReader<String> reader;

    public ConsoleCommandProvider() {
        reader = new ConsoleRequestReader();
        printPreview();
    }

    @Override
    public String nextCommand() {
        return reader.read();
    }

    private void printPreview() {
        System.out.println(RunnerConstant.PLEASE_ENTER_REQUEST_COMMAND.getValue());
        System.out.println(RunnerConstant.INFO_TO_VIEW_AVAILABLE_API_INFO.getValue());
        System.out.println(RunnerConstant.EXIT_TO_EXIT_FROM_PROGRAM.getValue());
    }
}
