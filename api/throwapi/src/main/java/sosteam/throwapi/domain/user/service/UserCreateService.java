package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.UserInfo;
import sosteam.throwapi.domain.user.entity.dto.user.UserSaveDto;
import sosteam.throwapi.domain.user.exception.PasswordDifFromConfirmException;
import sosteam.throwapi.domain.user.exception.UserAlreadyExistException;
import sosteam.throwapi.domain.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCreateService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserAuthSearchService userAuthSearchService;

    public User SignUp(UserSaveDto dto) {
        log.debug("Start SignUp User = {}", dto);
        if (!dto.getInputPassword().equals(dto.getInputPasswordCheck())) throw new PasswordDifFromConfirmException();

        //인증을 실제로 진행했는지 확인
        userAuthSearchService.IsSuccessIsTrue(dto.getEmail());

        // 1. create User Entity
        User user = new User(
                dto.getInputId(),
                passwordEncoder.encode(dto.getInputPassword()),
                dto.getSnsId(),
                dto.getSnsCategory()
        );

        // 2. create UserInfo Entity
        UserInfo userInfo = new UserInfo(
                dto.getUserName(),
                dto.getUserPhoneNumber(),
                dto.getEmail()
        );

        //modify User's data(userinfo, role, userStatus)
        user.modifyRole(dto.getRole());
        user.modifyUserStatus(dto.getUserStatus());

        //mapping User, UserInfo
        user.modifyUserInfo(userInfo);
        userInfo.modifyUser(user);

        // 이미 존재하는 회원을 추가 하려고 할 때 409 error
        User userResult = null;
        try {
            userResult = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error("Save User data values overlap = {}", e.getMessage());
            throw new UserAlreadyExistException();
        }

        return userResult;
    }
}
