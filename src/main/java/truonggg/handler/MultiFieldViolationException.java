package truonggg.handler;

import lombok.Getter;
import truonggg.response.ErrorCode;

import java.util.Map;

@Getter
public class MultiFieldViolationException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String domain;
    private final Map<String, String> fieldErrors;

    public MultiFieldViolationException(String message, ErrorCode errorCode, String domain, Map<String, String> fieldErrors) {
        super(message);
        this.errorCode = errorCode;
        this.domain = domain;
        this.fieldErrors = fieldErrors;
    }
}
