package sosteam.throwapi.global.exception.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NoContentException extends CommonException {
    public NoContentException() {
        super("NO_CONTENT","Nothing to show", HttpStatus.NO_CONTENT);
    }
}
