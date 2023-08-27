package sosteam.throwapi.domain.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.repository.repo.GifticonRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class GifticonDeleteService {
    private final GifticonRepository gifticonRepository;

    private Gifticon getGifticonByGiftTraceId(String giftTraceId) {
        return gifticonRepository.findByGiftTraceId(giftTraceId)
                .orElseThrow(() -> new EntityNotFoundException("Gifticon not found for given gift_trace_id"));
    }

    public void deleteGifticonByGiftTraceId(String giftTraceId) {
        Gifticon gifticon = getGifticonByGiftTraceId(giftTraceId);
        gifticonRepository.delete(gifticon);
    }
}
