package sosteam.throwapi.domain.oauth.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NonValidateTokenException extends CommonException {
    public NonValidateTokenException(){
        super("NON_VALIDATE_TOKEN_EXCEPTION", "유효하지 않는 token 입니다.", HttpStatus.CONFLICT);
    }
}
