package sosteam.throwapi.domain.oauth.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NonExistentUserInKakao extends CommonException {
    public NonExistentUserInKakao() {
        super("NON_EXISTENT_USER_IN_KAKAO", "카카오에 등록되지 않는 사용자 입니다.", HttpStatus.UNAUTHORIZED);
    }

}
