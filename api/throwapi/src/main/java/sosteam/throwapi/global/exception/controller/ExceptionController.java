package sosteam.throwapi.global.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sosteam.throwapi.global.exception.exception.CommonException;
import sosteam.throwapi.global.exception.exception.CommonExceptionResponse;

/**
 * 예외 처리 컨트롤러
 * CommonException을 이용하여 외부로 에러 정보가 나가는 것을 방지한다.
 * 각 Entity마다 exception을 제작, 사용한다.
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     * 정의된 CommonException을 처리하는 함수
     * ResponseEntity에 담아서 정의된 코드와 메시지, 상태정보를 넘겨준다.
     */
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonExceptionResponse> commonExceptionHandler(CommonException e) {
        return new ResponseEntity(
                new CommonExceptionResponse(
                        e.getCode(),
                        e.getMessage()
                ),
                e.getStatus()
        );
    }

    /**
     *
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonExceptionResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return new CommonExceptionResponse(
                "INVALID_REQUEST",
                e.getBindingResult().getFieldError().getDefaultMessage()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonExceptionResponse httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return new CommonExceptionResponse(
                "INVALID_JSON",
                "JSON 형식이 잘못되었습니다."
        );
    }
}
