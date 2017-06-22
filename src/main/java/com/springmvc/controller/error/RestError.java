package com.springmvc.controller.error;

import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RestError {
    private Map<String, Object> error;

    RestError(HttpServletRequest request, ErrorAttributes errorAttributes,
                     boolean enableStackTrace) {
        this.error = getErrorAttributes(request, errorAttributes, enableStackTrace);

        // Stack trace is enabled if manually enabled in instantiation and not disabled elsewhere
        if (enableStackTrace && includeStackTrace(request))
            addStackTrace();
    }

    void hideException() {
        error.remove("exception");
    }

    void hideMessage() {
        error.remove("message");
    }

    void hideTimestamp() {
        error.remove("timestamp");
    }

    private void addStackTrace() {
        String trace = (String) error.get("trace");

        if (trace != null)
            error.put("trace", trace.split("\n\t"));
    }

    private boolean includeStackTrace(HttpServletRequest request) {
        String parameter = request.getParameter("trace");

        return parameter != null && !"false".equals(parameter.toLowerCase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                   ErrorAttributes errorAttributes,
                                                   boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

    public Map<String, Object> getError() {
        return error;
    }
}
