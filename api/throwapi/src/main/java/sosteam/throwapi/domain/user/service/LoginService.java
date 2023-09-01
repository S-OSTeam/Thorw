package sosteam.throwapi.domain.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.oauth.entity.Tokens;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.login.ThrowLoginDto;
import sosteam.throwapi.domain.user.exception.NoSuchUserException;
import sosteam.throwapi.domain.user.exception.LoginFailException;
import sosteam.throwapi.domain.user.repository.UserRepository;
import sosteam.throwapi.global.service.TokensGenerateService;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokensGenerateService tokensGenerateService;

    public Tokens throwLogin(ThrowLoginDto throwLoginDto){ //아이디 비번 일치 하는지 확인 후 일치 하면 Tokens 을
        // inputId 를 이용해 User 정보를 불러 옴
        // 그 후 존재 하는 유저인지 예외 처리
        User user = userRepository.searchByInputId(throwLoginDto.getInputId());
        if(user == null){
            log.error("login fail no such user");
            throw new LoginFailException();
        }

        // 저장된 비밀번호와 match 여부 확인
        String userPW = user.getPassword();
        log.debug("userPw = {}", userPW);
        if(!passwordEncoder.matches(throwLoginDto.getInputPassword(), userPW)){
            log.error("login fail long password");
            throw new LoginFailException();
        }

        return tokensGenerateService.generate(user.getId(), user.getInputId());
    }
}
