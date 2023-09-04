package sosteam.throwapi.domain.user.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class DormantUserException extends CommonException {
    public DormantUserException() {
        super("DORMANT_USER", "휴면상태의 유저입니다.", HttpStatus.UNAUTHORIZED);
    }
}
