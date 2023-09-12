package sosteam.throwapi.domain.store.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class WrongStoreIdException extends CommonException {
    public WrongStoreIdException() {
        super("STORE_ID_DON'T_MATCH", "가게 아이디가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED);
    }
}
