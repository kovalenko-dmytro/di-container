package core.application.configuration.console;

import core.application.configuration.AppConfiguration;
import core.application.configuration.ArgumentsParser;
import core.application.configuration.constant.ConfigurationConstant;
import core.application.exception.ApplicationException;
import core.ioc.constant.ErrorMessage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConsoleArgumentsParser implements ArgumentsParser {

    @Override
    public void parse(String... args) throws ApplicationException {
        Map<String, String> arguments = parseArguments(args);
        Properties properties = loadProperties(arguments);
        AppConfiguration.getInstance().initProperties(properties);
    }

    private Map<String, String> parseArguments(String[] args) {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < args.length; i = i + 2) {
            if (args[i].startsWith(ConfigurationConstant.KEY_PREFIX.getValue()) && i + 1 < args.length) {
                result.put(args[i], args[i + 1]);
            }
        }
        return result;
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
