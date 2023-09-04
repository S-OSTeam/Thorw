package sosteam.throwapi.global.security.redis.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class DuplicationLoginException extends CommonException {
    public DuplicationLoginException(){
        super("LOGIN_FAILED", "중복 로그인은 불가능 합니다.", HttpStatus.UNAUTHORIZED);
    }
}
