package sosteam.throwapi.global.exception.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NotFoundException extends CommonException {
    public NotFoundException() {
        super("NOT_FOUND", "찾을 수 없습니다.",HttpStatus.NOT_FOUND);

    }
}
