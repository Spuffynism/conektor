package xyz.ndlr.model.provider.facebook;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.AsyncClientHttpRequestExecution;
import org.springframework.http.client.AsyncClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Logs restTemplate requests
 */
public class RequestLoggerInterceptor implements AsyncClientHttpRequestInterceptor {
    private static final Logger logger = Logger.getLogger(RequestLoggerInterceptor.class);

    @Override
    public ListenableFuture<ClientHttpResponse> intercept(HttpRequest httpRequest, byte[] body,
                                                          AsyncClientHttpRequestExecution
                                                                  asyncClientHttpRequestExecution)
            throws IOException {
        logRequest(httpRequest, body);

        ListenableFuture<ClientHttpResponse> response =
                asyncClientHttpRequestExecution.executeAsync(httpRequest, body);

        response.addCallback(this::logResponseSuccess, this::logResponseFailure);

        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        String requestMessage = formatRequestMessage(request.getMethod(), request.getURI(), body);
        logger.info(requestMessage);
    }

    private String formatRequestMessage(HttpMethod method, URI uri, byte[] body) {
        String methodStr = method.toString();
        String uriStr = uri.toString();
        String bodyStr = new String(body);

        return String.format("\n%s\n%s\n%s", methodStr, uriStr, bodyStr);
    }

    private void logResponseSuccess(ClientHttpResponse response) {
        String statusLine = null;

        try {
            HttpStatus statusCode = response.getStatusCode();
            String statusText = response.getStatusText();
            statusLine = formatResponseStatusLine(statusCode, statusText);
        } catch (IOException ignored) {
        }

        String body = tryReadResponseBody(response);
        String message = String.format("%s\n%s", statusLine, body);

        logger.info(message);
    }

    private String formatResponseStatusLine(HttpStatus httpStatus, String statusText) {
        return String.format("%s %s", httpStatus, statusText);
    }

    /**
     * see https://stackoverflow.com/a/35446009/5709703
     *
     * @param response the success response
     * @return the response's body
     */
    private String tryReadResponseBody(ClientHttpResponse response) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int readBytes;

        try {
            InputStream responseStream = response.getBody();

            while ((readBytes = responseStream.read(buffer)) != -1)
                result.write(buffer, 0, readBytes);
        } catch (IOException ignored) {
        }

        return result.toString();
    }

    private void logResponseFailure(Throwable throwable) {
        logger.error("Error during message sending", throwable);
    }
}
