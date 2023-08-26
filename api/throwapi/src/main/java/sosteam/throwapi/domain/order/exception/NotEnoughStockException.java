package sosteam.throwapi.domain.order.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NotEnoughStockException extends CommonException {
    public NotEnoughStockException() {
        super("NOT_ENOUGH_STOCK", HttpStatus.CONFLICT);
    }
}
