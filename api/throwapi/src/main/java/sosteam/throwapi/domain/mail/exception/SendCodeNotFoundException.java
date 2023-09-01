package sosteam.throwapi.domain.mail.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class SendCodeNotFoundException extends CommonException {
    public SendCodeNotFoundException() {

        super("SEND_CODE_NOT_FOUND", "이메일 인증 코드를 다시 전송 해주세요.", HttpStatus.NOT_FOUND);
    }
}
