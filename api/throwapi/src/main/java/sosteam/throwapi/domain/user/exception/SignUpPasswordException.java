package sosteam.throwapi.domain.user.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class SignUpPasswordException extends CommonException {
    public SignUpPasswordException(){
        super("SIGNUP_PASSWORD_NOT_SAME", "회원가입을 위한 비밀번호와 확인 비밀번호가 일치 하지 않습니다.", HttpStatus.CONFLICT);
    }
}
