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
import sosteam.throwapi.global.security.redis.entity.RedisRefreshToken;
import sosteam.throwapi.global.security.redis.repository.RefreshTokenRedisRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final AuthTokensGenerateService authTokensGenerateService;
    private final OAuthApiClientService oAuthApiClientService;


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
            UUID memberId = user.getId();
            String inputId = user.getInputId();
            authTokens = authTokensGenerateService.generate(memberId, inputId);
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
