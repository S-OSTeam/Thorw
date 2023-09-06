package sosteam.throwapi.domain.order.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class ValidateGifticonException extends CommonException {
    public ValidateGifticonException() {
        super("GIFTICON_NULL_OR_EMPTY","기프티콘이 NULL 혹은 EMPTY 입니다.",HttpStatus.NO_CONTENT);
    }
}
