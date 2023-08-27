package sosteam.throwapi.domain.store.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class StoreAlreadyExistException extends CommonException {
    public StoreAlreadyExistException() {
        super("STORE_ALREADY_EXIST", "해당 가게는 등록되어있습니다( 사업자 번호가 이미 등록되어있습니다).", HttpStatus.CONFLICT);
    }
}
