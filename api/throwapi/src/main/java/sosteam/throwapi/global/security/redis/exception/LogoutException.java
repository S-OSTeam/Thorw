package sosteam.throwapi.global.security.redis.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class LogoutException extends CommonException {
    public LogoutException(){
        super("LOGOUT_ACCOUNT", "로그아웃 된 계정입니다.", HttpStatus.UNAUTHORIZED);
    }
}
