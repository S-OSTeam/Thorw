package sosteam.throwapi.domain.order.repository.repoCustom;

import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.entity.Receipt;

import java.util.Set;
import java.util.UUID;

public interface ReceiptCustomRepository {
    Set<Receipt> searchByUserId(UUID userId);
    Gifticon createGifticonAndReceipt(String templateToken);
}
