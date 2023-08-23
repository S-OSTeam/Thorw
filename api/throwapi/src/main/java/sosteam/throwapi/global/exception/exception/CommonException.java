package sosteam.throwapi.global.exception.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonException extends RuntimeException{
    private final String code;
    private final String message;
    private final HttpStatus status;
}
