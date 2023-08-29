package sosteam.throwapi.domain.order.repository.repoCustom;

import sosteam.throwapi.domain.order.entity.Gifticon;

import java.util.Optional;

public interface GifticonCustomRepository {
    Optional<Gifticon> searchByGiftTraceId(String giftTraceId);
}
