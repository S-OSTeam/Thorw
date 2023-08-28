package sosteam.throwapi.domain.user.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class UserAlreadyExistException extends CommonException {
    public UserAlreadyExistException(){
        super("USER_ALREADY_EXIST_EXCEPTION", "이미 회원 가입 되어 있는 사용자 입니다.", HttpStatus.CONFLICT);
    }
}
