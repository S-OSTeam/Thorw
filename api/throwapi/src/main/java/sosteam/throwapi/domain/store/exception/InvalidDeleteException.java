package sosteam.throwapi.domain.store.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class InvalidDeleteException extends CommonException {

    public InvalidDeleteException() {
        super("INVALID_STORE_DELETE", "삭제하고자 하는 가게와 정보가 불일치합니다.",HttpStatus.BAD_REQUEST);
    }
}
