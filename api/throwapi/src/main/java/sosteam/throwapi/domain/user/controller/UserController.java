package sosteam.throwapi.domain.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.user.controller.request.LeaderBoardRequest;
import sosteam.throwapi.domain.user.controller.request.login.ThrowLoginRequest;
import sosteam.throwapi.domain.user.controller.request.user.IdDuplicateRequest;
import sosteam.throwapi.domain.user.controller.request.user.PWCheckRequest;
import sosteam.throwapi.domain.user.controller.request.user.UserCngRequest;
import sosteam.throwapi.domain.user.controller.request.user.UserSaveRequest;
import sosteam.throwapi.domain.user.controller.response.IdDuplicateResponse;
import sosteam.throwapi.domain.user.controller.response.PWCheckResponse;
import sosteam.throwapi.domain.user.controller.response.UserInfoResponse;
import sosteam.throwapi.domain.user.entity.SNSCategory;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.login.ThrowLoginDto;
import sosteam.throwapi.domain.user.entity.dto.user.*;
import sosteam.throwapi.domain.user.service.*;
import sosteam.throwapi.global.entity.Role;
import sosteam.throwapi.global.entity.UserStatus;
import sosteam.throwapi.global.service.JwtTokenService;

import java.util.Set;

@Controller
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserCreateService userCreateService;
    private final UserReadService userReadService;
    private final UserUpdateService userUpdateService;
    private final UserDeleteService userDeleteService;
    private final JwtTokenService jwtTokenService;
    private final MileageService mileageService;

    @PostMapping("/iddup")
    public ResponseEntity<IdDuplicateResponse> checkIdDup(@RequestBody @Valid IdDuplicateRequest params){
        IdDuplicationDto idDuplicationDto = new IdDuplicationDto(
                params.getInputId()
        );

        IdDuplicateResponse result = new IdDuplicateResponse(userReadService.checkIdDup(idDuplicationDto));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/pwcheck")
    public ResponseEntity<PWCheckResponse> checkPWAgain(
            @RequestHeader(name = "Authorization", required = true)
            @Pattern(regexp = "^(Bearer)\s.+$", message = "Bearer [accessToken]")
            String token,

            @RequestBody @Valid PWCheckRequest params
    ){
        // accessToken 을 뽑아 내어 inputId 를 구함(구할 때 token 에 대한 유효성 검사를 진행 함)
        String accessToken = token.substring(7);
        String inputId = jwtTokenService.extractSubject(accessToken);

        PWCheckDto pwCheckDto = new PWCheckDto(
                inputId,
                params.getInputPassword()
        );

        //pw 가 일치 하는지 확인
        PWCheckResponse result = new PWCheckResponse(userReadService.checkPWAgain(pwCheckDto));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid UserSaveRequest params) {
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

        userCreateService.SignUp(userSaveDto);
        return ResponseEntity.ok("정상 회원가입 완료");
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signOut(
            @RequestHeader(name = "Authorization", required = true)
            @Pattern(regexp = "^(Bearer)\s.+$", message = "Bearer [accessToken]")
            String token
    ){
        String accessToken = token.substring(7);
        SignOutDto signOutDto = new SignOutDto(
                jwtTokenService.extractSubject(accessToken)
        );

        userDeleteService.signOut(signOutDto);

        return ResponseEntity.ok("정상 회원 탈퇴 완료! 회원 정보는 한달관 보관됩니다.");
    }

    @PostMapping("/restorestatus")
    public ResponseEntity<String> restoreUserStatus(@RequestBody @Valid ThrowLoginRequest params){
        ThrowLoginDto throwLoginDto = new ThrowLoginDto(
                params.getInputId(),
                params.getInputPassword()
        );

        userUpdateService.restoreUserStatus(throwLoginDto);
        return ResponseEntity.ok("계정이 활성화 되었습니다.");
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

        User result = userReadService.searchByInputId(userInfoDto);

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
        userReadService.searchByInputId(userInfoDto);

        UserCngDto userCngDto = new UserCngDto(
                inputId,
                params.getUserName(),
                params.getUserPhoneNumber(),
                params.getEmail()
        );
        // 정보 변경
        userUpdateService.cngUser(userCngDto);

        return ResponseEntity.ok("회원 정보 변경 완료");
    }

    @PostMapping("/leaderboard")
    public ResponseEntity<LeaderBoardDto> leaderBoard(@RequestBody @Valid LeaderBoardRequest request){
        LeaderBoardDto leaderBoardDto = mileageService.searchMyMileageByInputId(request.getInputId());
        Set<LeaderBoardDto> leaderBoard = mileageService.createLeaderBoard();

    }
}
