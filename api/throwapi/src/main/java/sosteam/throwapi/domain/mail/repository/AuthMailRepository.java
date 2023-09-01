package sosteam.throwapi.domain.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sosteam.throwapi.domain.mail.entity.AuthMail;
import sosteam.throwapi.domain.mail.repository.custom.AuthMailRepositoryCustom;

import java.util.UUID;

@Repository
public interface AuthMailRepository extends JpaRepository<AuthMail, UUID>, AuthMailRepositoryCustom {
}
