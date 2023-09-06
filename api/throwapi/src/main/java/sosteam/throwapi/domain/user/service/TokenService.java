package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.oauth.entity.Tokens;
import sosteam.throwapi.domain.oauth.exception.NotValidateTokenException;
import sosteam.throwapi.domain.user.exception.ReissueException;
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
    private final UserReadService userReadService;
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
        userReadService.isUserStatusNormal(user.getUserStatus());

        Tokens tokens = tokensGenerateService.generate(memberId, user.getInputId());

//        //reissue 가 재 로그인 이기 때문에 redis 에 있는 refreshToken 을 갱신 할 필요가 있다.
//        //따라서 기존에 있던 refreshToken 을 지워 주고 새로 발급받은 refreshToken 을 저장 해 준다.
//        redisUtilService.deleteData(memberId.toString());
//        redisUtilService.setData(memberId.toString(), tokens.getRefreshToken());

        String redisRefreshToken = redisUtilService.getData(user.getId().toString());
        // reissue 를 위해 전송 된 refreshToken 과 로그인 시에 redis 에 저장 된 refreshToken 이 다르면 reissue 실패
        if(!redisRefreshToken.equals(tokensDto.getRefreshToken())){
            log.debug("redis = {}, local = {}", redisRefreshToken, tokensDto.getRefreshToken());
            throw new ReissueException();
        }

        // reissue 는 재 로그인 이기 때문에 로그인 때 저장 한 정보들을 갱신 할 필요가 있다.
        // 따라서 기존의 refreshToken 과 "login" 을 샂게 후 새롭게 발급 받은 정보를 저장 한다.
        // 우선 refreshToken 삭제
        redisUtilService.deleteData(memberId.toString());
        // 그 후 login 삭제
        redisUtilService.deleteData(user.getInputId());
        // key : UUID, value : refreshToken 으로 redis 에 50400 초 동안 저장
        // reissue 시 보안을 위해 저장
        redisUtilService.setRefreshToken(user.getId().toString(), tokens.getRefreshToken());
        // key : inputId, value : "login" 으로 redis 에 7200 초 동안 저장
        // 중복 로그인을 방지 하기 위함
        redisUtilService.setAccessToken(user.getInputId(), "login");

        return tokens;
    }
}
