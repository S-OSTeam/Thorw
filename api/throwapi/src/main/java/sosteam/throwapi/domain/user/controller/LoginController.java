package sosteam.throwapi.domain.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.oauth.entity.Tokens;
import sosteam.throwapi.domain.user.controller.request.login.ThrowLoginRequest;
import sosteam.throwapi.domain.user.controller.response.ReissueTokensResponse;
import sosteam.throwapi.domain.user.entity.dto.login.LogoutDto;
import sosteam.throwapi.domain.user.entity.dto.login.ReissueTokensDto;
import sosteam.throwapi.domain.user.entity.dto.login.ThrowLoginDto;
import sosteam.throwapi.domain.user.service.LoginService;
import sosteam.throwapi.domain.user.service.TokenService;

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
    private final LoginService loginService;
    @PostMapping("/reissue")
    public ResponseEntity<ReissueTokensResponse> reissueTokens(
            @RequestHeader(name = "grant_type", required = true)
            @Pattern(regexp = "^(refresh_token)", message = "reissue API 요청시 grant_type 은 refresh_token 만 가능")
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
    @PostMapping
    public ResponseEntity<Tokens> throwLogin(@RequestBody @Valid ThrowLoginRequest params){
        //dto에 옮겨 담기
        ThrowLoginDto throwLoginDto = new ThrowLoginDto(
                params.getInputId(),
                params.getInputPassword()
        );

        // 로그인에 성공 해서 예외처리 되지 않고 true 를 반환 했다면 토큰 발급 해 줌
        Tokens tokens = loginService.throwLogin(throwLoginDto);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(name = "Authorization", required = true)
            @Pattern(regexp = "^(Bearer)\s.+$", message = "Bearer [accessToken]")
            String token
    ){
        String accessToken = token.substring(7);
        log.debug("token = {}, accessToken = {}", token, accessToken);
        LogoutDto logoutDto = new LogoutDto(accessToken);
        loginService.logout(logoutDto);
        return ResponseEntity.ok("성공적으로 로그아웃 되었습니다.");
    }

    //test 계정에 대한 testToken 발급
    @PostMapping("/testtoken")
    public ResponseEntity<Tokens> testToken(){
        ThrowLoginDto throwLoginDto = new ThrowLoginDto(
                "testinputid",
                "testpassword1!"
        );

        Tokens tokens = loginService.throwLogin(throwLoginDto);
        return ResponseEntity.ok(tokens);
    }
}