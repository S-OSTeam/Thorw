package sosteam.throwapi.domain.store.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NoSuchStoreException extends CommonException {
    public NoSuchStoreException () {
        super("NO_SUCH_STORE","찾고자 하는 가게가 없습니다.",HttpStatus.NOT_FOUND);
    }
}
