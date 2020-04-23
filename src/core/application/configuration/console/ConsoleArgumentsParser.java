package core.application.configuration.console;

import core.application.configuration.AppConfiguration;
import core.application.configuration.ArgumentsParser;
import core.application.configuration.constant.ConfigurationConstant;
import core.application.exception.ApplicationException;
import core.ioc.constant.ErrorMessage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class ConsoleArgumentsParser implements ArgumentsParser {

    @Override
    public Map<String, String> parse(String... args) throws ApplicationException {
        Map<String, String> arguments = parseArguments(args);
        Properties properties = loadProperties(arguments);
        AppConfiguration.getInstance().initProperties(properties);
        return arguments;
    }

    private Map<String, String> parseArguments(String[] args) {
        return Arrays.stream(args)
            .filter(arg -> arg.startsWith(ConfigurationConstant.KEY_PREFIX.getValue()))
            .map(entry -> entry.split(ConfigurationConstant.ARG_ASSIGN.getValue()))
            .collect(Collectors.toMap(entry -> entry[0].trim(), entry -> entry[1].trim()));
    }

    private Properties loadProperties(Map<String, String> arguments) throws ApplicationException {
        Properties result = new Properties();
        InputStream in;
        try {
            in = arguments.containsKey(ConfigurationConstant.CONFIG_KEY.getValue())
                ? new FileInputStream(arguments.get(ConfigurationConstant.CONFIG_KEY.getValue()))
                : getClass().getResourceAsStream(ConfigurationConstant.CONFIGURATION_PROPERTIES_DEFAULT_FILE_PATH.getValue());
            result.load(in);
            return result;
        } catch (Exception e) {
            throw new ApplicationException(ErrorMessage.CANNOT_CONFIGURE_PROPERTIES.getValue());
        }
    }
}
