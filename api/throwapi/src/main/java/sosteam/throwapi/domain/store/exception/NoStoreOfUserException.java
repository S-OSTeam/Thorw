package sosteam.throwapi.domain.store.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NoStoreOfUserException extends CommonException {
    public NoStoreOfUserException() {
        super("NO_STORE_OF_USER", "해당 사용자의 가게가 없습니다.", HttpStatus.NOT_FOUND);
    }
}
