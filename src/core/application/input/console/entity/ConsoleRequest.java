package core.application.input.console.entity;

import java.util.Map;

public class ConsoleRequest {

    private String requestPath;
    private Map<String, String> requestParameters;

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(Map<String, String> requestParameters) {
        this.requestParameters = requestParameters;
    }
}
