package core.application.configuration;

import core.application.exception.ApplicationException;
import core.ioc.constant.ErrorMessage;

import java.util.Objects;
import java.util.Properties;

public class AppConfiguration {

    private Properties properties;

    private AppConfiguration() {}

    private static class AppConfigurationHolder {
        private static final AppConfiguration instance = new AppConfiguration();
    }

    public static AppConfiguration getInstance() {
        return AppConfigurationHolder.instance;
    }

    public void initProperties(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() throws ApplicationException {
        checkInitProperties();
        return properties;
    }

    public String getProperty(String propertyName) throws ApplicationException {
        checkInitProperties();
        return properties.getProperty(propertyName);
    }

    private void checkInitProperties() throws ApplicationException {
        if (Objects.isNull(properties)) {
            throw new ApplicationException(ErrorMessage.APP_PROPERTIES_NOT_INIT.getValue());
        }
    }
}
