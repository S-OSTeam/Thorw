package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.oauth.entity.Tokens;
import sosteam.throwapi.domain.oauth.exception.NotValidateTokenException;
import sosteam.throwapi.global.security.redis.service.RedisUtilService;
import sosteam.throwapi.global.service.TokensGenerateService;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.login.ReissueTokensDto;
import sosteam.throwapi.domain.user.repository.UserRepository;
import sosteam.throwapi.global.service.JwtTokenService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final TokensGenerateService tokensGenerateService;
    private final UserInfoService userInfoService;
    private final RedisUtilService redisUtilService;

    public Tokens reissueTokens(ReissueTokensDto tokensDto){
        String subject = jwtTokenService.extractSubject(tokensDto.getRefreshToken());
        log.debug("subject = {}", subject);
        UUID memberId = UUID.fromString(subject);
        log.debug("memberId = {}", memberId);

        User user = userRepository.searchById(memberId);

        if(user == null){
            throw new NotValidateTokenException();
        }

        //user 계정의 현재 상태를 확인
        userInfoService.isUserStatusNormal(user.getUserStatus());

        Tokens tokens = tokensGenerateService.generate(memberId, user.getInputId());

        //reissue 가 재 로그인 이기 때문에 redis 에 있는 refreshToken 을 갱신 할 필요가 있다.
        //따라서 기존에 있던 refreshToken 을 지워 주고 새로 발급받은 refreshToken 을 저장 해 준다.
        redisUtilService.deleteData(memberId.toString());
        redisUtilService.setData(memberId.toString(), tokens.getRefreshToken());
        return tokens;
    }
}
