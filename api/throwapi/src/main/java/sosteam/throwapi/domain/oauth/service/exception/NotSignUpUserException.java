package sosteam.throwapi.domain.oauth.service.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NotSignUpUserException extends CommonException {
    public NotSignUpUserException() {
        super("NOT_SIGNUP_USER", "등록되지 않은 유저 입니다.", HttpStatus.UNAUTHORIZED);
    }
}
