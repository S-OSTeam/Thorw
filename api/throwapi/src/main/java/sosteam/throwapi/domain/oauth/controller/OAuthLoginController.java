package sosteam.throwapi.domain.oauth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sosteam.throwapi.domain.oauth.controller.request.OAuthLoginRequest;
import sosteam.throwapi.domain.oauth.entity.AuthTokens;
import sosteam.throwapi.domain.oauth.entity.dto.OAuthLoginDto;
import sosteam.throwapi.domain.oauth.service.OAuthLoginService;
import sosteam.throwapi.domain.oauth.service.kakaoImpl.KakaoLoginParamsService;
import sosteam.throwapi.global.entity.SNSCategory;

@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthLoginController {
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody OAuthLoginRequest params) {
        log.debug("in OAuth login Kakao");
        log.debug("params = {}", params);
        OAuthLoginDto oAuthLoginDto = new OAuthLoginDto(
                SNSCategory.valueOf(params.getSns()),
                params.getAccessToken(),
                params.getRefreshToken()
        );

        AuthTokens result = oAuthLoginService.login(oAuthLoginDto);

        return ResponseEntity.ok(result);
    }
}
