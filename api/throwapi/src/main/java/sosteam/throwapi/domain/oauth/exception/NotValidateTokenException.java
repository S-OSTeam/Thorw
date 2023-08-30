package sosteam.throwapi.domain.oauth.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NotValidateTokenException extends CommonException {
    public NotValidateTokenException(){
        super("NOT_VALIDATE_TOKEN_EXCEPTION", "유효하지 않는 token 입니다.", HttpStatus.CONFLICT);
    }
}
