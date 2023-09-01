package sosteam.throwapi.domain.user.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.oauth.entity.Tokens;
import sosteam.throwapi.domain.oauth.service.AuthTokensGenerateService;
import sosteam.throwapi.domain.user.controller.request.UserInfoRequest;
import sosteam.throwapi.domain.user.controller.response.ReissueTokensResponse;
import sosteam.throwapi.domain.user.entity.dto.*;
import sosteam.throwapi.domain.user.repository.UserRepository;
import sosteam.throwapi.domain.user.service.TokenService;
import sosteam.throwapi.global.service.JwtTokenService;

import java.util.UUID;

/**
 * 일반 로그인 및 sns 로그인 구현
 * 일반 로그인의 경우 id, pwd만을 입력 받는다.
 * <p>
 * sns 로그인은 카카오톡, 네이버, 구글을 이용한다.
 */

@Controller
@Slf4j
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final AuthTokensGenerateService authTokensGenerateService;

    @PostMapping("/reissue")
    public ResponseEntity<ReissueTokensResponse> reissueTokens(
            @RequestHeader(name = "grant_type", required = true)
            @Pattern(regexp = "^(refresh_token)", message = "reissue API 요청시 grant_type 은 refreshToken 만 가능")
            String grantType,

            @RequestHeader(name = "refresh_token", required = true)
            String refreshToken
    ){
        ReissueTokensDto tokensDto = new ReissueTokensDto(
                refreshToken
        );

        Tokens result = tokenService.reissueTokens(tokensDto);
        return ResponseEntity.ok(new ReissueTokensResponse(
                result.getAccessToken(),
                result.getRefreshToken()
        ));
    }

//    @PostMapping("testToken")
//    public ResponseEntity<Tokens> testToken(@RequestBody UserInfoRequest params){
//        UUID id = userRepository.searchUUIDByInputId(params.getInputId());
//        Tokens tokens = authTokensGenerateService.generate(id, params.getInputId());
//        return ResponseEntity.ok(tokens);
//    }

}
