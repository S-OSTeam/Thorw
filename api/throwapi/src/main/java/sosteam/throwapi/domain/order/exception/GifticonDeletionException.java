package sosteam.throwapi.domain.order.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class GifticonDeletionException extends CommonException {
    public GifticonDeletionException() {
        super("CAN_NOT_GIFTICON_DELETION","해당 기프티콘을 삭제할 수 없습니다.",HttpStatus.FORBIDDEN);
    }
}
