package sosteam.throwapi.domain.order.repository.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sosteam.throwapi.domain.order.entity.Item;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}
