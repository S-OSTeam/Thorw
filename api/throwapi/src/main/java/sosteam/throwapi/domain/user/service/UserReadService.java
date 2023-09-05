package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.IdDuplicationDto;
import sosteam.throwapi.domain.user.entity.dto.user.PWCheckDto;
import sosteam.throwapi.domain.user.entity.dto.user.UserInfoDto;
import sosteam.throwapi.domain.user.exception.DormantUserException;
import sosteam.throwapi.domain.user.exception.NoSuchUserException;
import sosteam.throwapi.domain.user.exception.SignOutUserException;
import sosteam.throwapi.domain.user.repository.UserRepository;
import sosteam.throwapi.global.entity.UserStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserReadService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserAuthSearchService userAuthSearchService;


    public boolean checkIdDup(IdDuplicationDto idDuplicationDto) {
        User user = userRepository.searchByInputId(idDuplicationDto.getInputId());
        return user != null;
    }

    public User searchByInputId(UserInfoDto userInfoDto) {
        //inputId 를 기반으로 User 정보를 불러 옴
        User user = userRepository.searchByInputId(userInfoDto.getInputId());
        if (user == null) throw new NoSuchUserException();

        //user 계정의 상태를 확인 한다
        this.isUserStatusNormal(user.getUserStatus());

        return user;
    }

    public boolean checkPWAgain(PWCheckDto pwCheckDto){
        // inputId 를 기반으로 user 정보를 가져 옴
        User user = userRepository.searchByInputId(pwCheckDto.getInputId());
        if (user == null) throw new NoSuchUserException();

        return passwordEncoder.matches(pwCheckDto.getInputPassword(), user.getPassword());
    }

    public boolean isUserStatusNormal(UserStatus userStatus){
        if(userStatus == UserStatus.NORMAL){
            return true;
        } else if(userStatus == UserStatus.DORMANT){
            throw new DormantUserException();
        } else if(userStatus == UserStatus.SIGNOUT){
            throw new SignOutUserException();
        }
        return false;
    }

    public String searchInputIdByEmail(String email) {
        //실제 인증을 진행 했는지 확인
        userAuthSearchService.IsSuccessIsTrue(email);

        //email로 User의 uuid를 찾는다.
        User user = userRepository.searchByEmail(email);


        //찾아온 id가 없거나 비어있는 경우 NOT_FOUND
        if (user == null) {
            throw new NoSuchUserException();
        }
        log.debug("find id : {}", user.getInputId());

        //user 계정의 상태를 확인 한다
        this.isUserStatusNormal(user.getUserStatus());

        return user.getInputId();
    }
}
