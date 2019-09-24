package http.request;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static http.common.HeaderFields.*;

public class Parameters {
    private final Map<String, String> parameters;

    public Parameters(String parameterString) {
        parameters = new HashMap<>();
        if (StringUtils.isBlank(parameterString)) {
            return;
        }
        parse(parameterString);
    }

    private void parse(String parameterString) {
        Arrays.asList(parameterString.split(AMPERSAND)).forEach(parameter -> {
            List<String> tokens = Arrays.asList(parameter.split(EQUAL));
            parameters.put(tokens.get(0), tokens.get(1));
        });
    }

    public String getParameter(String parameter) {
        return parameters.get(parameter);
    }

    public boolean contains(String parameter) {
        return parameters.containsKey(parameter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameters that = (Parameters) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String param : parameters.keySet()) {
            sb.append(param).append(COLON).append(BLANK).append(parameters.get(param)).append(NEWLINE);
        }
        return sb.toString();
    }
}