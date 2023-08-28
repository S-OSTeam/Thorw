package sosteam.throwapi.domain.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.oauth.entity.AuthTokens;
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
    private final AuthTokensGenerateService authTokensGenerateService;
    private final OAuthApiClientService oAuthApiClientService;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;


    public AuthTokens login(OAuthLoginParamsService params){
        log.debug("in login");
        log.debug("params {}", params);

        String accessToken = oAuthApiClientService.requestAccessToken(params);
        log.debug("accessToken{}", accessToken);

        String snsId = oAuthApiClientService.requestOauthInfo(accessToken); // try catch 로 snsId 가 null 인지 확인 하세욧

        log.debug("snsId{}", snsId);

        AuthTokens authTokens;
        if(userRepository.existBySNSId(snsId)){
            User user = userRepository.findBySNSId(snsId);
            UUID memberId = user.getId();
            authTokens = authTokensGenerateService.generate(memberId);

//            refreshTokenRedisRepository.save(
//                    RedisRefreshToken.builder()
//                            .id(user.getInputId())
//                            .refreshToken(authTokens.getRefreshToken())
//                            .accessToken(authTokens.getAccessToken())
//                            .build()
//            );
        } else {
            throw new NotSignUpUserException();
        }
        return authTokens;
    }

}
