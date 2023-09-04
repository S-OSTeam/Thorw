package sosteam.throwapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.exception.UserAuthNotFoundException;
import sosteam.throwapi.domain.user.repository.UserAuthRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAuthSearchService {
    private final UserAuthRepository userAuthRepository;

    public boolean searchIsSuccess(String email) {
        Boolean isSuccess = userAuthRepository.existSuccessByEmail(email);

        if (isSuccess == null) {
            throw new UserAuthNotFoundException();
        }

        return isSuccess;
    }

    public boolean IsSuccessIsTrue(String email) {
        boolean isSuccess = searchIsSuccess(email);

        if (!isSuccess) {
            throw new UserAuthNotFoundException();
        }

        return true;
    }
}
