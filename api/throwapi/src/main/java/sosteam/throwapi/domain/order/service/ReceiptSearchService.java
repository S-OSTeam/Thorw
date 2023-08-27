package sosteam.throwapi.domain.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.order.repository.repo.GifticonRepository;
import sosteam.throwapi.domain.order.repository.repo.ReceiptRepository;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiptSearchService {
    private final GifticonRepository gifticonRepository;
    private final ReceiptRepository receiptRepository;

    /**
     * 기프티콘 고유 Id로 영수증 찾기
     * @param giftTraceId : 기프티콘 고유 Id
     * @return 해당 영수증
     */
    public Receipt findReceiptByGiftTraceId(String giftTraceId) {
        log.debug("START : findReceiptByGiftTraceId");
        Gifticon gifticon = gifticonRepository.findByGiftTraceId(giftTraceId)
                .orElseThrow(() -> new EntityNotFoundException("Gifticon not found for given gift_trace_id"));
        return gifticon.getReceipt();
    }

    /**
     * User Id로 영수증들 찾기
     * @param userId : user Id
     * @return 영수증 리스트
     */
    public Set<Receipt> findReceiptsByUserId(UUID userId) {
        return receiptRepository.findByUser_Id(userId);
    }
}
