package sosteam.throwapi.domain.order.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class KakaoException extends CommonException {
    public KakaoException(HttpStatus status) {
        super("KAKAO_EXCEPT", "카카오 API 예외", status);
    }
}
