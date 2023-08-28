package sosteam.throwapi.domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sosteam.throwapi.domain.store.log.CrnLog;

public interface CrnLogRepository extends JpaRepository<CrnLog,Long> {
}
