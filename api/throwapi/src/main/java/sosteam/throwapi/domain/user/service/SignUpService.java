package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.controller.request.UserSaveRequest;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.UserInfo;
import sosteam.throwapi.domain.user.entity.dto.UserSaveDto;
import sosteam.throwapi.domain.user.exception.UserAlreadyExistException;
import sosteam.throwapi.domain.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpService {
    private final UserRepository userRepository;

    public User SignUp(UserSaveDto dto){
        // if User already SignUp
        // return 409 : Conflict http status
        log.debug("Start SignUp User = {}", dto);
        User userExist = userRepository.findByInputId(dto.getInputId());
        if(userExist == null) throw new UserAlreadyExistException();

        // 1. create User Entity
        User user = new User(
                dto.getInputId(),
                dto.getInputPassWord(),
                dto.getSnsId(),
                dto.getSnsCategory()
        );

        // 2. create UserInfo Entity
        UserInfo userInfo = new UserInfo(
                dto.getName(),
                dto.getPhoneNumber(),
                dto.getEmail()
        );

        //modify User's data(userinfo, role, userStatus)
        user.modifyRole(dto.getRole());
        user.modifyUserStatus(dto.getUserStatus());

        //mapping User, UserInfo
        user.modifyUserInfo(userInfo);
        userInfo.modifyUser(user);

        return userRepository.save(user);
    }
}
