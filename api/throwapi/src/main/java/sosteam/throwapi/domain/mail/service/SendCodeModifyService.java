package sosteam.throwapi.domain.mail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.mail.exception.SendCodeNotFoundException;
import sosteam.throwapi.domain.mail.repository.AuthMailRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendCodeModifyService {

    private final AuthMailRepository authMailRepository;

    public boolean modifySendCode(String email) {
        log.debug("modifySendCode : {}", email);

        //인증 유효기간은 현재로 부터 10분뒤 이므로 endAt에 10분이 추가되어 저장 된다.
        long isSuccessRepo = authMailRepository.modifyIsSuccessByEmail(email);

        if (isSuccessRepo == 0) {
            throw new SendCodeNotFoundException();
        }

        log.debug("end");
        return isSuccessRepo != 0;
    }

}
