package sosteam.throwapi.domain.order.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NoSuchGifticonException extends CommonException {
    public NoSuchGifticonException() {
        super("NO_SUCH_GIFTICON","찾고자 하는 기프티콘이 없습니다.",HttpStatus.NOT_FOUND);
    }
}
