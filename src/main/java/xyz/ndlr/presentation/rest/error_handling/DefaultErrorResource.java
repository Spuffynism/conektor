package xyz.ndlr.presentation.rest.error_handling;

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
@RequestMapping(DefaultErrorResource.ERROR_PATH)
public class DefaultErrorResource implements ErrorController {

    static final String ERROR_PATH = "/error";
    private final ErrorAttributes errorAttributes;

    @Autowired
    public DefaultErrorResource(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        RestError restError = new RestError(request, errorAttributes, false);

        restError.showMessage();
        restError.showTimestamp();
        restError.showException();

        return new ResponseEntity<>(restError.getErrors(), HttpStatus.OK);
    }
}
