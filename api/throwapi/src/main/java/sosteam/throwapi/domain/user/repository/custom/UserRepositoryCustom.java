package sosteam.throwapi.domain.user.repository.custom;

import sosteam.throwapi.domain.user.entity.User;

import java.util.UUID;

public interface UserRepositoryCustom {
    User searchBySNSId(String snsId);

    User searchByInputId(String inputId);
    User searchById(UUID id);

    boolean existBySNSId(String snsId);
}
