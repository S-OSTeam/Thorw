package sosteam.throwapi.global.security.redis.exception;

import org.springframework.http.HttpStatus;
import sosteam.throwapi.global.exception.exception.CommonException;

public class NoSuchDataRedisException extends CommonException {
    public NoSuchDataRedisException(){
        super("NO_SUCH_DATA_REDIS", "레디스에 해당 데이터가 없어 처리 할 수 없습니다.", HttpStatus.UNAUTHORIZED);
    }
}
