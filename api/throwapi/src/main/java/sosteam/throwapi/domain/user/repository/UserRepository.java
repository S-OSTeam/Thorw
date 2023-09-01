package sosteam.throwapi.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.repository.custom.UserRepositoryCustom;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryCustom {
}
