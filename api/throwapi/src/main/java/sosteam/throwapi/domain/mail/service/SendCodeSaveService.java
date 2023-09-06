package sosteam.throwapi.domain.mail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.mail.entity.AuthMail;
import sosteam.throwapi.domain.mail.repository.AuthMailRepository;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendCodeSaveService {
    private final AuthMailRepository authMailRepository;

    public AuthMail saveAuthMail(String sendCode, String email) {
        log.debug("saveAuthMail : {} {}", email, sendCode);
        //인증 유효기간은 현재로 부터 10분뒤 이므로 endAt에 10분이 추가되어 저장 된다.
        AuthMail authMail = new AuthMail(sendCode, LocalDateTime.now().plusMinutes(10), email, false);
        authMailRepository.save(authMail);

        log.debug("end");
        return authMail;
    }
}
