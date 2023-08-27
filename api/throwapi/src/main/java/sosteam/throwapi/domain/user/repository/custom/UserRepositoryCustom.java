package sosteam.throwapi.domain.user.repository.custom;

import sosteam.throwapi.domain.user.entity.User;

public interface UserRepositoryCustom {
    User findBySNSId(String snsId);

    boolean existBySNSId(String snsId);
}
