package sosteam.throwapi.domain.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.oauth.entity.AuthTokens;
import sosteam.throwapi.domain.oauth.entity.dto.OAuthLoginDto;
import sosteam.throwapi.domain.oauth.exception.NonExistentUserInKakao;
import sosteam.throwapi.domain.oauth.exception.NonValidateTokenException;
import sosteam.throwapi.domain.oauth.exception.NotSignUpUserException;
import sosteam.throwapi.domain.oauth.service.kakaoImpl.KakaoLoginParamsService;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.repository.UserRepository;
import sosteam.throwapi.global.security.redis.entity.RedisRefreshToken;
import sosteam.throwapi.global.security.redis.repository.RefreshTokenRedisRepository;
import sosteam.throwapi.global.service.JwtTokenService;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final AuthTokensGenerateService authTokensGenerateService;
    private final OAuthApiClientService oAuthApiClientService;
    private final JwtTokenService jwtTokenService;


    public AuthTokens login(OAuthLoginDto oAuthLoginDto){
        log.debug("in login service");
        log.debug("oAuthLoginDto = {}", oAuthLoginDto);

        String snsId = null;
        String accessToken = null;

        // 카카오 token 의 유효성 검사
        if(!oAuthApiClientService.requestTokenValidation(oAuthLoginDto.getAccessToken())){
            //accessToken 이 유효하지 않을 때 refreshToken 를 통해 재 발급
            try{
                accessToken = oAuthApiClientService.reissueAccessToken(oAuthLoginDto.getRefreshToken());
            } catch (Exception e){
                log.debug("accessToken, refreshToken are nonValidation");
                throw new NonValidateTokenException();
            }
        }

        // 요청으로 들어 온 kakao accessToken 을 이용해 kakao 고유 id 를 뽑아 냄
        // 이때 refreshToken 을 이용해 accessToken 을 재 발급 받았다면 재 입력된 AccessToken 이 아닌 재발급 된 accessToken 을 사용한다.
        if(accessToken == null){
            snsId = oAuthApiClientService.requestOAuthId(oAuthLoginDto.getAccessToken());
        } else {
            snsId = oAuthApiClientService.requestOAuthId(accessToken);
        }

        // token 으로 부터 얻어낸 Id 값을 확인 함
        if(snsId == null){
            throw new NonExistentUserInKakao();
        }

        log.debug("snsId = {}", snsId);

        AuthTokens authTokens;
        if(userRepository.existBySNSId(snsId)){
            User user = userRepository.findBySNSId(snsId);
            UUID memberId = user.getId();
            authTokens = authTokensGenerateService.generate(memberId);
            log.debug("authTokens = {}", authTokens);

            refreshTokenRedisRepository.save(
                    RedisRefreshToken.builder()
                            .id(user.getInputId())
                            .refreshToken(authTokens.getRefreshToken())
                            .accessToken(authTokens.getAccessToken())
                            .build()
            );
            log.debug("oauth login Success");
        } else {
            throw new NotSignUpUserException();
        }
        return authTokens;
    }
}
