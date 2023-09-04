package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.UserInfo;
import sosteam.throwapi.domain.user.entity.dto.login.ThrowLoginDto;
import sosteam.throwapi.domain.user.entity.dto.user.*;
import sosteam.throwapi.domain.user.exception.*;
import sosteam.throwapi.domain.user.repository.UserRepository;
import sosteam.throwapi.global.entity.UserStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {
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

    public void signOut(SignOutDto signOutDto){
        User user = userRepository.searchByInputId(signOutDto.getInputId());
        if (user == null) throw new NoSuchUserException();

        //user 계정의 상태를 확인 한다
        this.isUserStatusNormal(user.getUserStatus());

        log.debug("inputId = {}, UserStatus = {}", signOutDto.getInputId(), UserStatus.SIGNOUT);

        Long result = userRepository.updateUserStatusByInputId(signOutDto.getInputId(), UserStatus.SIGNOUT);
    }


    public boolean checkIdDup(IdDuplicationDto idDuplicationDto) {
        User user = userRepository.searchByInputId(idDuplicationDto.getInputId());
        return user != null;
    }

    public void cngUser(UserCngDto userCngDto) {
        Long result = userRepository.updateByInputId(userCngDto);
        log.debug("result cng User Service = {}", result.toString());
    }

    public void restoreUserStatus(ThrowLoginDto throwLoginDto){
        User user = userRepository.searchByInputId(throwLoginDto.getInputId());
        // 존재 하는 user 인지 확인
        if (user == null) throw new NoSuchUserException();

        userAuthSearchService.IsSuccessIsTrue(user.getUserInfo().getEmail());


        //user 의 status 를 normal 로 변경
        Long result = userRepository.updateUserStatusByInputId(throwLoginDto.getInputId(), UserStatus.NORMAL);
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

    //userStatus 에 따라 NORMAL 이 아닐 경우 해당하는 Exception throw
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
