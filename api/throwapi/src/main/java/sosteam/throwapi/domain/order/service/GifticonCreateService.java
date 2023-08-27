package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.repository.repo.GifticonRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class GifticonCreateService {
    private final GifticonRepository gifticonRepository;

    /**
     * 기프티콘 생성
     * @param gifticon
     * @return 기프티콘 정보
     */
    public Gifticon createGifticon(Gifticon gifticon) {
        return gifticonRepository.save(gifticon);
    }
}
