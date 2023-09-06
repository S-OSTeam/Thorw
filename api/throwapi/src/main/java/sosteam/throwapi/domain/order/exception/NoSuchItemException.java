package sosteam.throwapi.domain.order.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NoSuchItemException extends CommonException {
    public NoSuchItemException() {
        super("NO_SUCH_ITEM","찾고자 하는 아이템이 없습니다.", HttpStatus.NOT_FOUND);
    }
}
