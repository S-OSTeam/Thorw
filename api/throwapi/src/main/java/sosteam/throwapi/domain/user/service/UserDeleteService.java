package sosteam.throwapi.domain.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.SignOutDto;
import sosteam.throwapi.domain.user.exception.NoSuchUserException;
import sosteam.throwapi.domain.user.repository.UserRepository;
import sosteam.throwapi.global.entity.UserStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDeleteService {
    private final UserRepository userRepository;
    private final UserReadService userReadService;

    public void signOut(SignOutDto signOutDto){
        User user = userRepository.searchByInputId(signOutDto.getInputId());
        if (user == null) throw new NoSuchUserException();

        //user 계정의 상태를 확인 한다
        userReadService.isUserStatusNormal(user.getUserStatus());

        log.debug("inputId = {}, UserStatus = {}", signOutDto.getInputId(), UserStatus.SIGNOUT);

        Long result = userRepository.updateUserStatusByInputId(signOutDto.getInputId(), UserStatus.SIGNOUT);
    }
}
