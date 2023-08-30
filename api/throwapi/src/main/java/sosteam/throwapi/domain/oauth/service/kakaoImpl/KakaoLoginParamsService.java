package sosteam.throwapi.domain.oauth.service.kakaoImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import sosteam.throwapi.domain.oauth.service.OAuthLoginParamsService;
import sosteam.throwapi.domain.user.entity.SNSCategory;

@Getter
@NoArgsConstructor
public class KakaoLoginParamsService implements OAuthLoginParamsService {
    private String authorizationCode;

    @Override
    public SNSCategory oAuthProvider(){
        return SNSCategory.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody(){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }
}
