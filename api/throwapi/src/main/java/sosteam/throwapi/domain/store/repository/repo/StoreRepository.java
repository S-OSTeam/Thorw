package sosteam.throwapi.domain.store.repository.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.repository.repoCustom.StoreCustomRepository;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID>, StoreCustomRepository {
}
