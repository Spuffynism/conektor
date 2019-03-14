package xyz.ndlr.presentation.rest.error_handling;

import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

class RestError {
    private final Map<String, Object> filteredErrors;
    private final Map<String, Object> errors;

    RestError(HttpServletRequest request, ErrorAttributes errorAttributes,
              boolean enableStackTrace) {
        this.errors = getErrorAttributes(request, errorAttributes, enableStackTrace);

        // Stack trace is enabled if manually enabled in instantiation and not disabled elsewhere
        if (enableStackTrace && includeStackTrace(request))
            addStackTrace();

        this.filteredErrors = new HashMap<>();
    }

    private void addStackTrace() {
        String trace = (String) errors.get("trace");

        if (trace != null)
            errors.put("trace", trace.split("\n\t"));
    }

    private boolean includeStackTrace(HttpServletRequest request) {
        String parameter = request.getParameter("trace");

        return parameter != null && !parameter.equalsIgnoreCase(String.valueOf(false));
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                   ErrorAttributes errorAttributes,
                                                   boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

    Map<String, Object> getErrors() {
        return filteredErrors;
    }

    void showException() {
        addFilteredProperty("exception");
    }

    void showMessage() {
        addFilteredProperty("message");
    }

    void showTimestamp() {
        addFilteredProperty("timestamp");
    }

    private void addFilteredProperty(String name) {
        filteredErrors.put(name, errors.get(name));
    }
}
