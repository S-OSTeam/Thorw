package sosteam.throwapi.domain.store.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NoSuchRegistrationNumberException extends CommonException {
    public NoSuchRegistrationNumberException() {
            super("NO_SUCH_REGISTRATION_NUMBER", "국세청에 등록되지 않은 사업자등록번호입니다.", HttpStatus.UNAUTHORIZED);
    }
}
