package sosteam.throwapi.domain.user.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class AuthCodeDifException extends CommonException {
    public AuthCodeDifException() {
        super("USER_AUTH_FAILURE", "이메일 인증 코드를 다시 확인 해주세요.", HttpStatus.BAD_REQUEST);
    }
}
