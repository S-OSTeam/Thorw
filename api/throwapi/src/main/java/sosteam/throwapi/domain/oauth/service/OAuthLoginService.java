package sosteam.throwapi.domain.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.oauth.entity.AuthTokens;
import sosteam.throwapi.domain.oauth.service.exception.NotSignUpUserException;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.repository.UserRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final AuthTokensGenerateService authTokensGenerateService;
    private final OAuthApiClientService oAuthApiClientService;


    public AuthTokens login(OAuthLoginParamsService params){
        log.info("in login");
        log.info("params {}", params);

        String accessToken = oAuthApiClientService.requestAccessToken(params);
        log.info("accessToken{}", accessToken);

        String snsId = oAuthApiClientService.requestOauthInfo(accessToken);
        log.info("snsId{}", snsId);

        AuthTokens authTokens;
        if(userRepository.existBySNSId(snsId)){
            User user = userRepository.findBySNSId(snsId);
            UUID memberId = user.getId();
            authTokens = authTokensGenerateService.generate(memberId);
        } else {
            throw new NotSignUpUserException();
        }
        return authTokens;
    }

}
