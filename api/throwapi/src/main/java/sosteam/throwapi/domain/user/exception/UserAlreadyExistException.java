package sosteam.throwapi.domain.user.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class UserAlreadyExistException extends CommonException {
    public UserAlreadyExistException(){
        super("USER_ALREADY_EXIST_EXCEPTION", "저장 하려는 데이터 중 이미 있는데이터와 중복되는 값이 존재 합니다.", HttpStatus.CONFLICT);
    }
}
