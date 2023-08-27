package sosteam.throwapi.domain.store.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class BiznoAPIException extends CommonException {
    public BiznoAPIException(int resultCode) {
        super("BIZON_CALL_FAILED", "비즈노 API 호출 관련 오류, Error Code : " + String.valueOf(resultCode), HttpStatus.BAD_GATEWAY);
    }
}
