package sosteam.throwapi.domain.user.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class UserAuthNotFoundException extends CommonException {
    public UserAuthNotFoundException() {

        super("USER_AUTH_NOT_FOUND", "이메일 인증을 다시 진행 해주세요.", HttpStatus.NOT_FOUND);
    }
}
