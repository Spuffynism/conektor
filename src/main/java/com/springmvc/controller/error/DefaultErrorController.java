package com.springmvc.controller.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/error")
public class DefaultErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";
    private final ErrorAttributes errorAttributes;

    @Autowired
    public DefaultErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        RestError restError = new RestError(request, errorAttributes, false);

        restError.hideException();
        //restError.hideMessage();
        restError.hideTimestamp();

        return new ResponseEntity<>(restError.getError(), HttpStatus.OK);
    }
}
