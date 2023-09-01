package sosteam.throwapi.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sosteam.throwapi.domain.user.entity.UserAuth;
import sosteam.throwapi.domain.user.repository.custom.UserAuthRepositoryCustom;

import java.util.UUID;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, UUID>, UserAuthRepositoryCustom {
}
