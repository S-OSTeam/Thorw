package sosteam.throwapi.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sosteam.throwapi.domain.user.controller.request.user.UserEmailRequest;
import sosteam.throwapi.domain.user.controller.request.user.UserPwdRequest;
import sosteam.throwapi.domain.user.service.UserReadService;
import sosteam.throwapi.domain.user.service.UserUpdateService;


/**
 * id찾기, 비밀번호 찾기(?) 등의 id 비밀번호 관련 서비스
 */
@Controller
@Slf4j
@RequestMapping("/search")
@RequiredArgsConstructor
public class IdPwSearchController {
    private final UserReadService userReadService;
    private final UserUpdateService userUpdateService;

    /**
     * 이메일을 입력받은 후 가입된 inputId를 반환한다.
     * 없으면 405 NOT_FOUND를 던진다.
     *
     * @return email로 가입된 id
     */
    @GetMapping("/id")
    public ResponseEntity<String> searchUserInputIdByEmail(@RequestBody @Valid UserEmailRequest request) {
        String email = request.getEmail();

        //가입된 이메일이 있는지 확인하고 없으면 서비스에서 NOT_FOUND를 던진다.
        String inputId = userReadService.searchInputIdByEmail(email);


        return ResponseEntity.ok(inputId);
    }

    /**
     * 이메일을 입력받은 후 가입된 inputId를 반환한다.
     *
     * @return email
     */

    @PostMapping("/pwd")
    public ResponseEntity<String> changePwdByEmail(@RequestBody @Valid UserPwdRequest request) {
        String email = request.getEmail();

        //비밀번호가 일치하는지 확인, 인증이 있는지 확인 후 비밀번호를 변경한다.
        userUpdateService.modifyUserPwdByEamil(email, request.getInputPassword(), request.getInputPasswordCheck());

        return ResponseEntity.ok(request.getEmail());
    }
}
