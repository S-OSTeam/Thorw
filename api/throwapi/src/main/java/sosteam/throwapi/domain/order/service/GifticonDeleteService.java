package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.order.entity.ReceiptStatus;
import sosteam.throwapi.domain.order.exception.GifticonDeletionException;
import sosteam.throwapi.domain.order.repository.repo.GifticonRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GifticonDeleteService {
    private final GifticonRepository gifticonRepository;
    private final GifticonSearchService gifticonSearchService;

    /**
     * 기프티콘 삭제
     * @param gifticonId
     */
    public void deleteGifticon(UUID gifticonId) {
        log.debug("GIFTICON DELETE");
        Receipt receipt = gifticonSearchService.searchGifticonById(gifticonId).get().getReceipt();
        // 기프티콘 상태확인
        if(receipt.getReceiptStatus() == ReceiptStatus.SALE){
            throw new GifticonDeletionException();
        }
        gifticonRepository.deleteById(gifticonId);
    }
}
