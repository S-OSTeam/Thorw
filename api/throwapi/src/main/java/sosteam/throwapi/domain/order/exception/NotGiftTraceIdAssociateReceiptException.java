package sosteam.throwapi.domain.order.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NotGiftTraceIdAssociateReceiptException extends CommonException {
    public NotGiftTraceIdAssociateReceiptException() {
        super("NOT_GIFTRACEID_ASSOCIATE_RECEIPT", "주어진 GiftTraceId와 관련된 영수증을 찾을 수 없습니다", HttpStatus.NOT_FOUND);
    }
}
