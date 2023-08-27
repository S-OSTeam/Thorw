package sosteam.throwapi.domain.oauth.service;

import org.springframework.util.MultiValueMap;
import sosteam.throwapi.global.entity.SNSCategory;

public interface OAuthLoginParamsService {
    SNSCategory oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
