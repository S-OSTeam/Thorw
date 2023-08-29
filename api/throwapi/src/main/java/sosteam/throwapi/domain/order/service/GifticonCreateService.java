package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.exception.ValidateGifticonException;
import sosteam.throwapi.domain.order.repository.repo.GifticonRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class GifticonCreateService {
    private final GifticonRepository gifticonRepository;

    /**
     * 기프티콘 생성
     * @param gifticon
     * @return 저장하는 기프티콘
     */
    @Transactional
    public Gifticon createGifticon(Gifticon gifticon) {
        log.debug("GIFTICON CREATE");
        validateGifticon(gifticon); // 유효성 검사
        return gifticonRepository.save(gifticon);
    }

    /**
     * 유효성 검사
     * @param gifticon
     */
    private void validateGifticon(Gifticon gifticon) {
        if (gifticon.getGiftTraceId() == null || gifticon.getGiftTraceId().isEmpty()) {
            throw new ValidateGifticonException();
        }
    }
}
