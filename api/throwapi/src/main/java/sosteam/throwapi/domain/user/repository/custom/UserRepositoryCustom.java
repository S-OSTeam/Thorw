package sosteam.throwapi.domain.user.repository.custom;

import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.UserCngDto;
import sosteam.throwapi.global.entity.UserStatus;

import java.util.Set;
import java.util.UUID;

public interface UserRepositoryCustom {
    User searchBySNSId(String snsId);

    User searchByInputId(String inputId);

    User searchById(UUID id);

    UUID searchUUIDByInputId(String inputId);

    Long modifyByInputId(UserCngDto userCngDto);

    Long modifyUserStatusByInputId(String inputId, UserStatus userStatus);

    boolean existBySNSId(String snsId);

    User searchByEmail(String email);

    Long modifyPwdByUserId(UUID userId, String pwd);

    Long searchRankByMileage(Long mileage);

    Set<User> searchTop10UsersByMileage();

    Long modifyMileageByInputId(String inputId, Long acMileage);
}
