package sosteam.throwapi.domain.store.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NoContentException extends CommonException {
    public NoContentException() {
        super("", "", HttpStatus.NO_CONTENT);
    }
}
