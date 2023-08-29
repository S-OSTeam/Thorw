package sosteam.throwapi.domain.oauth.service;

import sosteam.throwapi.global.entity.SNSCategory;

public interface OAuthApiClientService {
    SNSCategory oAuthProvider();
    String requestAccessToken(OAuthLoginParamsService params);
    String requestOAuthId(String accessToken);
    boolean requestTokenValidation(String accessToken);
}
