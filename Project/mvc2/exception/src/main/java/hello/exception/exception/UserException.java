package hello.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserException extends RuntimeException{
    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    protected UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UserException() {
        super();
    }


}
