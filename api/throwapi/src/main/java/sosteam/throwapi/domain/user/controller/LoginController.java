package sosteam.throwapi.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 일반 로그인 및 sns 로그인 구현
 * 일반 로그인의 경우 id, pwd만을 입력 받는다.
 * <p>
 * sns 로그인은 카카오톡, 네이버, 구글을 이용한다.
 */

@Controller
@RequestMapping("/login")
public class LoginController {
}
