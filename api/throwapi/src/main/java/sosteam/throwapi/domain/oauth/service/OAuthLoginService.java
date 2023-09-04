package sosteam.throwapi.domain.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.oauth.entity.Tokens;
import sosteam.throwapi.domain.oauth.entity.dto.OAuthLoginDto;
import sosteam.throwapi.domain.oauth.exception.NotExistentUserInKakao;
import sosteam.throwapi.domain.oauth.exception.NotValidateTokenException;
import sosteam.throwapi.domain.oauth.exception.NotSignUpUserException;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.repository.UserRepository;
import sosteam.throwapi.domain.user.service.UserInfoService;
import sosteam.throwapi.global.security.redis.entity.RedisTokens;
import sosteam.throwapi.global.security.redis.repository.RefreshTokenRedisRepository;
import sosteam.throwapi.global.security.redis.service.RedisUtilService;
import sosteam.throwapi.global.service.TokensGenerateService;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final RedisUtilService redisUtilService;
    private final TokensGenerateService authTokensGenerateService;
    private final OAuthApiClientService oAuthApiClientService;
    private final UserInfoService userInfoService;


    public Tokens login(OAuthLoginDto oAuthLoginDto){
        log.debug("in login service");
        log.debug("oAuthLoginDto = {}", oAuthLoginDto);


        // 카카오 token 의 유효성 검사
        if(!oAuthApiClientService.requestTokenValidation(oAuthLoginDto.getAccessToken())) throw new NotValidateTokenException();

        // 요청으로 들어 온 kakao accessToken 을 이용해 kakao 고유 id 를 뽑아 냄
        String snsId = oAuthApiClientService.requestOAuthId(oAuthLoginDto.getAccessToken());

        // token 으로 부터 얻어낸 Id 값을 확인 함
        if(snsId == null){
            throw new NotExistentUserInKakao();
        }

        log.debug("snsId = {}", snsId);

        Tokens authTokens;
        if(userRepository.existBySNSId(snsId)){
            User user = userRepository.searchBySNSId(snsId);

            //user 계정의 현재 상태를 확인
            userInfoService.isUserStatusNormal(user.getUserStatus());

            UUID memberId = user.getId();
            String inputId = user.getInputId();
            authTokens = authTokensGenerateService.generate(memberId, inputId);
            log.debug("authTokens = {}", authTokens);

            redisUtilService.setData(memberId.toString(), authTokens.getRefreshToken());
            log.debug("oauth login Success");
        } else {
            throw new NotSignUpUserException();
        }
        return authTokens;
    }
}
