package sosteam.throwapi.domain.store.log;

import org.springframework.data.jpa.repository.JpaRepository;
import sosteam.throwapi.domain.store.log.CrnLog;

public interface CrnLogRepository extends JpaRepository<CrnLog,Long> {
}
