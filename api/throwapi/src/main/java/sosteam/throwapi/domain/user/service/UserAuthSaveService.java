package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.entity.UserAuth;
import sosteam.throwapi.domain.user.repository.UserAuthRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAuthSaveService {

    private final UserAuthRepository userAuthRepository;

    public UserAuth saveUserAuth(String email, boolean isSuccess) {
        UserAuth userAuth = new UserAuth(email, isSuccess);

        //userAuth 생성 후 저장 한 뒤 return
        userAuthRepository.save(userAuth);
        return userAuth;
    }
}
