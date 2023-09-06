package sosteam.throwapi.domain.user.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class ReissueException extends CommonException {
    public ReissueException() {
        super("REISSUE_ERROR", "reissue 에 사용 하는 refreshToken 에 문제가 있습니다.", HttpStatus.BAD_REQUEST);
    }
}
