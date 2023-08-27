package sosteam.throwapi.domain.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.user.controller.request.UserSaveRequest;
import sosteam.throwapi.domain.user.entity.dto.UserSaveDto;
import sosteam.throwapi.global.entity.Role;
import sosteam.throwapi.global.entity.SNSCategory;
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
public class LoginController {

    @PostMapping("/signUp")
    public void SignUp(@RequestBody UserSaveRequest params){
        UserSaveDto dto = new UserSaveDto(
                params.getInputId(),
                params.getInputPassWord(),
                params.getSnsId(),
                SNSCategory.valueOf(params.getSns()),
                UserStatus.valueOf(params.getUserStatus()),
                Role.valueOf(params.getRole()),
                params.getName(),
                params.getPhoneNumber(),
                params.getEmail()
        );


    }


}
