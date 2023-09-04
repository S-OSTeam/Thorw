package sosteam.throwapi.domain.mail.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class AlreadySendCodeException extends CommonException {
    public AlreadySendCodeException() {

        super("ALREADY_SEND_CODE", "이메일 인증 코드 대기 시간 입니다.", HttpStatus.BAD_REQUEST);
    }
}
