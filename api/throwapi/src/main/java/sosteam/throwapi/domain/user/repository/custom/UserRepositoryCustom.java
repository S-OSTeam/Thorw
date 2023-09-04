package sosteam.throwapi.domain.user.repository.custom;

import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.UserCngDto;

import java.util.UUID;

public interface UserRepositoryCustom {
    User searchBySNSId(String snsId);

    User searchByInputId(String inputId);

    User searchById(UUID id);

    UUID searchUUIDByInputId(String inputId);

    Long updateByInputId(UserCngDto userCngDto);

    boolean existBySNSId(String snsId);

    User searchByEmail(String email);

    Long updatePwdByUserId(UUID userId, String pwd);
}
