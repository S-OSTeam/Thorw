package sosteam.throwapi.domain.order.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class DuplicateGiftcionException extends CommonException {
    public DuplicateGiftcionException() {
        super("GIFTICON_DUPLICATE","기프티콘 중복이 있습니다.",HttpStatus.CONFLICT);
    }
}
