package sosteam.throwapi.global.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CommonException extends RuntimeException{
    private final String code;
    private String message;
    private final HttpStatus status;
}
