package sosteam.throwapi.domain.order.repository.repoCustom;

import sosteam.throwapi.domain.order.entity.Item;

import java.util.Optional;
import java.util.Set;

public interface ItemCustomRepository {
    Optional<Item> searchByProductName(String productName);
    Optional<Set<Item>> searchByProductNameContaining(String productName);
}
