package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.UserInfo;
import sosteam.throwapi.domain.user.entity.dto.user.IdDuplicationDto;
import sosteam.throwapi.domain.user.entity.dto.user.UserCngDto;
import sosteam.throwapi.domain.user.entity.dto.user.UserInfoDto;
import sosteam.throwapi.domain.user.entity.dto.user.UserSaveDto;
import sosteam.throwapi.domain.user.exception.NoSuchUserException;
import sosteam.throwapi.domain.user.exception.SignUpPasswordException;
import sosteam.throwapi.domain.user.exception.UserAlreadyExistException;
import sosteam.throwapi.domain.user.exception.UserAuthNotFoundException;
import sosteam.throwapi.domain.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserAuthSearchService userAuthSearchService;

    public User SignUp(UserSaveDto dto) {
        log.debug("Start SignUp User = {}", dto);
        if (!dto.getInputPassword().equals(dto.getInputPasswordCheck())) throw new SignUpPasswordException();

        //인증을 실제로 진행했는지 확인
        boolean isSuccess = userAuthSearchService.searchIsSuccess(dto.getEmail());

        if (!isSuccess) {
            throw new UserAuthNotFoundException();
        }

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

    public boolean checkIdDup(IdDuplicationDto idDuplicationDto) {
        User user = userRepository.searchByInputId(idDuplicationDto.getInputId());
        return user != null;
    }

    public void cngUser(UserCngDto userCngDto) {
        Long result = userRepository.updateByInputId(userCngDto);
        log.info("result cng User Service = {}", result.toString());
    }

    public User searchByInputId(UserInfoDto userInfoDto) {
        //inputId 를 기반으로 User 정보를 불러 옴
        User user = userRepository.searchByInputId(userInfoDto.getInputId());
        if (user == null) throw new NoSuchUserException();

        return user;
    }

}
