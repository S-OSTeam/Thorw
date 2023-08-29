package sosteam.throwapi.domain.oauth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sosteam.throwapi.domain.oauth.entity.AuthTokens;
import sosteam.throwapi.domain.oauth.service.OAuthLoginService;
import sosteam.throwapi.domain.oauth.service.kakaoImpl.KakaoLoginParamsService;

@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthLoginController {
    private final OAuthLoginService oAuthLoginService;
    

    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParamsService params) {
        log.debug("in loginKakao");
        AuthTokens result = oAuthLoginService.login(params);

        return ResponseEntity.ok(result);
    }
}
