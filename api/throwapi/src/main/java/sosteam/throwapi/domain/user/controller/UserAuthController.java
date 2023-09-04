package sosteam.throwapi.domain.user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sosteam.throwapi.domain.mail.service.SendCodeSearchService;
import sosteam.throwapi.domain.user.controller.request.AuthCodeCheckRequest;
import sosteam.throwapi.domain.user.exception.AuthCodeDifException;
import sosteam.throwapi.domain.user.service.UserAuthSaveService;

/**
 * 유저의 각종 인증 관련을 확인해주는 컨트롤러
 * 이메일 인증
 * /auth/code - 이메일 인증 코드가 일치하는지 확인
 */

@RequestMapping("/auth")
@Slf4j
@Controller
@RequiredArgsConstructor
public class UserAuthController {

    private final SendCodeSearchService sendCodeSearchService;
    private final UserAuthSaveService userAuthSaveService;

    @PostMapping("/code")
    public ResponseEntity<String> checkMailAuthCode(@RequestBody @Valid AuthCodeCheckRequest authCodeCheckRequest) {
        String email = authCodeCheckRequest.getEmail();
        String checkCode = authCodeCheckRequest.getCheckCode();

        log.debug("check mail auth code controller : {} {}");

        //확인하기 위해 코드를 가져온다. 없거나 잘못된 경우 에러를 던진다.
        String sendCode = sendCodeSearchService.searchSendCode(email);

        //일치하는지 확인 한다.
        boolean isSuccess = checkCode.equals(sendCode);

        //인증 시도 로그를 저장한다.
        userAuthSaveService.saveUserAuth(email, isSuccess);

        //입력된 코드와 저장된 코드가 다른 경우 에러를 던진다.
        if (!isSuccess) {
            throw new AuthCodeDifException();
        }

        log.debug("controller end");
        return ResponseEntity.ok("ok");
    }
}
