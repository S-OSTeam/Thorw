package sosteam.throwapi.domain.user.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NoSuchUserException extends CommonException {
    public NoSuchUserException(){
        super("NO_SUCH_USER", "해당 회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }
}
