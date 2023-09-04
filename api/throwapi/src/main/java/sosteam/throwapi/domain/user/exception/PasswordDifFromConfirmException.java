package sosteam.throwapi.domain.user.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class PasswordDifFromConfirmException extends CommonException {
    public PasswordDifFromConfirmException() {
        super("CONFIRM_PASSWORD_NOT_SAME", "비밀번호와 확인 비밀번호가 일치 하지 않습니다.", HttpStatus.CONFLICT);
    }
}
