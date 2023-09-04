package sosteam.throwapi.domain.user.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class LoginFailException extends CommonException {
    public LoginFailException(){
        super("LOGIN_FAILED", "로그인에 실패 하였습니다.", HttpStatus.UNAUTHORIZED);
    }
}
