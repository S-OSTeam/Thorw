package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.login.ThrowLoginDto;
import sosteam.throwapi.domain.user.entity.dto.user.UserCngDto;
import sosteam.throwapi.domain.user.exception.NoSuchUserException;
import sosteam.throwapi.domain.user.exception.PasswordDifFromConfirmException;
import sosteam.throwapi.domain.user.repository.UserRepository;
import sosteam.throwapi.global.entity.UserStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUpdateService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserAuthSearchService userAuthSearchService;

    public void restoreUserStatus(ThrowLoginDto throwLoginDto){
        User user = userRepository.searchByInputId(throwLoginDto.getInputId());
        // 존재 하는 user 인지 확인
        if (user == null) throw new NoSuchUserException();

        userAuthSearchService.IsSuccessIsTrue(user.getUserInfo().getEmail());


        //user 의 status 를 normal 로 변경
        Long result = userRepository.updateUserStatusByInputId(throwLoginDto.getInputId(), UserStatus.NORMAL);
    }

    public void cngUser(UserCngDto userCngDto) {
        Long result = userRepository.updateByInputId(userCngDto);
        log.debug("result cng User Service = {}", result.toString());
    }

    public Long modifyUserPwdByEamil(String email, String pwd, String confirmPwd) {
        //실제 인증을 진행 했는지 확인
        userAuthSearchService.IsSuccessIsTrue(email);

        log.debug("change pwd : {}", pwd);

        //확인 비밀번호가 같은지 확인
        if (!pwd.equals(confirmPwd)) {
            throw new PasswordDifFromConfirmException();
        }

        //email로 user의 uuid를 가져오기 위해 user를 조회
        User user = userRepository.searchByEmail(email);

        if (user == null) {
            throw new NoSuchUserException();
        }

        log.debug("find user : {}", user.getInputId());
        //비밀번호를 암호화
        String encodedPwd = passwordEncoder.encode(pwd);

        //같다면 변경
        Long result = userRepository.updatePwdByUserId(user.getId(), encodedPwd);

        return result;
    }
}
