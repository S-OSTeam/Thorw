package sosteam.throwapi.domain.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.user.controller.request.user.IdDuplicateRequest;
import sosteam.throwapi.domain.user.controller.request.user.PWCheckRequest;
import sosteam.throwapi.domain.user.controller.request.user.UserCngRequest;
import sosteam.throwapi.domain.user.controller.request.user.UserSaveRequest;
import sosteam.throwapi.domain.user.controller.response.IdDuplicateResponse;
import sosteam.throwapi.domain.user.controller.response.PWCheckResponse;
import sosteam.throwapi.domain.user.controller.response.UserInfoResponse;
import sosteam.throwapi.domain.user.entity.SNSCategory;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.*;
import sosteam.throwapi.domain.user.service.UserInfoService;
import sosteam.throwapi.global.entity.Role;
import sosteam.throwapi.global.entity.UserStatus;
import sosteam.throwapi.global.service.JwtTokenService;

@Controller
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService userInfoService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/iddup")
    public ResponseEntity<IdDuplicateResponse> checkIdDup(@RequestBody @Valid IdDuplicateRequest params){
        IdDuplicationDto idDuplicationDto = new IdDuplicationDto(
                params.getInputId()
        );

        IdDuplicateResponse result = new IdDuplicateResponse(userInfoService.checkIdDup(idDuplicationDto));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/pwcheck")
    public ResponseEntity<PWCheckResponse> checkPWAgain(
            @RequestHeader(name = "Authorization", required = true)
            @Pattern(regexp = "^(Bearer)\s.+$", message = "Bearer [accessToken]")
            String token,

            @RequestBody @Valid PWCheckRequest params
    ){
        String accessToken = token.substring(7);
        String inputId = jwtTokenService.extractSubject(accessToken);

        PWCheckDto pwCheckDto = new PWCheckDto(
                inputId,
                params.getInputPassword()
        );

        PWCheckResponse result = new PWCheckResponse(userInfoService.checkPWAgain(pwCheckDto));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@RequestBody @Valid UserSaveRequest params){
        UserSaveDto userSaveDto = new UserSaveDto(
                params.getInputId(),
                params.getInputPassword(),
                params.getInputPasswordCheck(),
                params.getSnsId(),
                SNSCategory.valueOf(params.getSns()),
                UserStatus.valueOf(params.getUserStatus()),
                Role.valueOf(params.getRole()),
                params.getUserName(),
                params.getUserPhoneNumber(),
                params.getEmail()
        );

        userInfoService.SignUp(userSaveDto);
        return ResponseEntity.ok("정상 회원가입 완료");
    }

    //inputId 를 이용해 User 의 info 를 반환 하는 API
    @GetMapping
    public ResponseEntity<UserInfoResponse> searchByInputId(
            @RequestHeader(name = "Authorization", required = true)
            @Pattern(regexp = "^(Bearer)\s.+$", message = "Bearer [accessToken]")
            String token
    ){
        String accessToken = token.substring(7);
        //accessToken 을 이용해 inputId 를 구함
        UserInfoDto userInfoDto = new UserInfoDto(
                jwtTokenService.extractSubject(accessToken)
        );

        User result = userInfoService.searchByInputId(userInfoDto);

        return ResponseEntity.ok(new UserInfoResponse(
                result.getInputId(),
                result.getRole(),
                result.getUserInfo().getUserName(),
                result.getUserInfo().getUserPhoneNumber(),
                result.getUserInfo().getEmail()
        ));
    }

    @PostMapping("/cnginfo")
    public ResponseEntity<String> cngUserInfo(
            @RequestHeader(name = "Authorization", required = true)
            @Pattern(regexp = "^(Bearer)\s.+$", message = "Bearer [accessToken]")
            String token,
            @RequestBody @Valid UserCngRequest params
    ){
        String accessToken = token.substring(7);
        String inputId = jwtTokenService.extractSubject(accessToken);

        UserInfoDto userInfoDto = new UserInfoDto(
                inputId
        );
        // 존재 하는 회원인지 확인
        userInfoService.searchByInputId(userInfoDto);

        UserCngDto userCngDto = new UserCngDto(
                inputId,
                params.getUserName(),
                params.getUserPhoneNumber(),
                params.getEmail()
        );
        // 정보 변경
        userInfoService.cngUser(userCngDto);

        return ResponseEntity.ok("회원 정보 변경 완료");
    }

}
