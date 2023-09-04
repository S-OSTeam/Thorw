package sosteam.throwapi.domain.mail.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class MailServiceUnavailableException extends CommonException {
    public MailServiceUnavailableException() {

        super("MAIL_SERVICE_UNAVAILABLE", "메일 전송 서비스 점검중입니다.", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
