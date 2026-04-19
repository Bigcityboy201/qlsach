package truonggg.handler;

import lombok.Getter;
import truonggg.response.ErrorCode;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String domain;

    public BusinessException(String message, ErrorCode errorCode, String domain) {
        super(message);
        this.errorCode = errorCode;
        this.domain = domain;
    }
}
