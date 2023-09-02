package sosteam.throwapi.domain.store.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class InvalidRequestException extends CommonException {
    public InvalidRequestException(String code) {
        super("INVALID_REQUEST_OF_"+code, "검증 실패! : 잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
    }
}
