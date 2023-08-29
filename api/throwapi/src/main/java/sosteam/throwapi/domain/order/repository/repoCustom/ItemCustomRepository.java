package sosteam.throwapi.domain.order.repository.repoCustom;

import sosteam.throwapi.domain.order.entity.Item;

import java.util.Optional;

public interface ItemCustomRepository {
    Optional<Item> searchByProductName(String productName);
}
