package sosteam.throwapi.domain.user.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class ModifyMileageException extends CommonException {

    public ModifyMileageException() {
        super("CAN_NOT_MODIFY_MILEAGE", "마일리지 수정에 실패했습니다.", HttpStatus.BAD_GATEWAY);
    }
}
