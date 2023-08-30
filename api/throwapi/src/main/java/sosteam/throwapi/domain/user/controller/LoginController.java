package sosteam.throwapi.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.oauth.entity.Tokens;
import sosteam.throwapi.domain.user.controller.request.IdDuplicateRequest;
import sosteam.throwapi.domain.user.controller.request.ReissueTokenRequest;
import sosteam.throwapi.domain.user.controller.request.UserSaveRequest;
import sosteam.throwapi.domain.user.controller.response.IdDuplicateResponse;
import sosteam.throwapi.domain.user.controller.response.ReissueTokensResponse;
import sosteam.throwapi.domain.user.entity.dto.IdDuplicationDto;
import sosteam.throwapi.domain.user.entity.dto.ReissueTokensDto;
import sosteam.throwapi.domain.user.entity.dto.UserSaveDto;
import sosteam.throwapi.domain.user.service.SignUpService;
import sosteam.throwapi.domain.user.service.TokenService;
import sosteam.throwapi.global.entity.Role;
import sosteam.throwapi.domain.user.entity.SNSCategory;
import sosteam.throwapi.global.entity.UserStatus;

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
    private final SignUpService signUpService;
    private final TokenService tokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@RequestBody @Valid UserSaveRequest params){
        UserSaveDto userSaveDto = new UserSaveDto(
                params.getInputId(),
                params.getInputPassword(),
                params.getSnsId(),
                SNSCategory.valueOf(params.getSns()),
                UserStatus.valueOf(params.getUserStatus()),
                Role.valueOf(params.getRole()),
                params.getUserName(),
                params.getUserPhoneNumber(),
                params.getEmail()
        );

        signUpService.SignUp(userSaveDto);
        return ResponseEntity.ok("정상 회원가입 완료");
    }

    @PostMapping("/idduptest")
    public ResponseEntity<IdDuplicateResponse> checkIdDup(@RequestBody @Valid IdDuplicateRequest params){
        IdDuplicationDto idDuplicationDto = new IdDuplicationDto(
                params.getInputId()
        );

        IdDuplicateResponse result = new IdDuplicateResponse(signUpService.checkIdDup(idDuplicationDto));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueTokensResponse> reissueTokens(@RequestBody @Valid ReissueTokenRequest params){
        ReissueTokensDto tokensDto = new ReissueTokensDto(
                SNSCategory.valueOf(params.getSns()),
                null,
                params.getRefreshToken()
        );

        Tokens result = tokenService.reissueTokens(tokensDto);
        return ResponseEntity.ok(new ReissueTokensResponse(
                params.getSns(),
                result.getAccessToken(),
                result.getRefreshToken()
        ));
    }
}
