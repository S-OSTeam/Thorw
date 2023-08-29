package sosteam.throwapi.domain.order.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class CreateTokenException extends CommonException {
    public CreateTokenException() {
        super("CAN_NOT_CREATE_TOKEN","토큰 생성이 안 됐습니다.",HttpStatus.NOT_FOUND);
    }
}
