package sosteam.throwapi.domain.order.repository.repoCustom;

import sosteam.throwapi.domain.order.entity.Receipt;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ReceiptCustomRepository {
    Optional<Set<Receipt>> searchByUserId(UUID userId);
}
