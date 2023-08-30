package sosteam.throwapi.domain.order.repository.repoCustom;

import sosteam.throwapi.domain.order.entity.Item;

import java.util.Optional;
import java.util.Set;

public interface ItemCustomRepository {
    Optional<Set<Item>> searchByProductName(String productName);
}
