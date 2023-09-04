package sosteam.throwapi.domain.user.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class SignOutUserException extends CommonException {
    public SignOutUserException() {
        super("SIGNOUT_USER", "회원 탈퇴 유저입니다.", HttpStatus.UNAUTHORIZED);
    }
}
