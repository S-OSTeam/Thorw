package sosteam.throwapi.domain.store.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class WrongStoreCodeException extends CommonException {
    public WrongStoreCodeException() {
        super("STORE_CODE_DON'T_MATCH", "가게 코드가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
