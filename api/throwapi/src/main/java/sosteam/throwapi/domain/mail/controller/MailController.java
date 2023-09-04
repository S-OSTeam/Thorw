package sosteam.throwapi.domain.mail.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sosteam.throwapi.domain.mail.controller.request.AuthCodeSendRequest;
import sosteam.throwapi.domain.mail.service.MailService;
import sosteam.throwapi.domain.mail.service.SendCodeSaveService;

/**
 * 메일 전송 관련 컨트롤러
 * 유저의 인증 메일 전송과 비밀번호 변경 안내, 마일리지 사용 및 환불 내역 안내 등의 메일을 전송해준다.
 * /mail/auth - 이메일 인증 전송 api
 */

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;
    private final SendCodeSaveService sendCodeSaveService;

    /**
     * 인증 메일을 전송한다.
     * 해당 인증 코드를 service에서 받아 온 다음, AuthCode테이블에 정보를 저장한다.
     * 인증 유효 기간은 10분으로 설정한다.
     */
    @PostMapping("/auth")
    public ResponseEntity<String> authMailSend(AuthCodeSendRequest authCodeSendRequest) {
        String email = authCodeSendRequest.getEmail();
        //이메일을 보낸 후 저장하기 위해 인증 코드를 받아온다.
        String sendCode = mailService.sendMail(email);

        //보낸 인증 코드를 따로 저장한다.
        sendCodeSaveService.saveAuthMail(sendCode, email);

        return ResponseEntity.ok("10m start count");
    }

    @GetMapping
    public ResponseEntity<String> testtest(){
        return ResponseEntity.ok("why not?");
    }
}
