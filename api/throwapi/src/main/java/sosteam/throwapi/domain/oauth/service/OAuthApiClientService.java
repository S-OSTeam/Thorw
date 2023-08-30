package sosteam.throwapi.domain.oauth.service;

import sosteam.throwapi.domain.user.entity.SNSCategory;

public interface OAuthApiClientService {
    SNSCategory oAuthProvider();
    String reissueAccessToken(String refreshToken);
    String requestOAuthId(String accessToken);
    boolean requestTokenValidation(String accessToken);
}
