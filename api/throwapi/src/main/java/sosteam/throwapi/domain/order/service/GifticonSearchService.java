package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.order.entity.ReceiptStatus;
import sosteam.throwapi.domain.order.exception.NoSuchGifticonException;
import sosteam.throwapi.domain.order.exception.NotGiftTraceIdAssociateReceiptException;
import sosteam.throwapi.domain.order.repository.repo.GifticonRepository;
import sosteam.throwapi.domain.order.repository.repo.ReceiptRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class GifticonSearchService {
    private final GifticonRepository gifticonRepository;
    private final ReceiptRepository receiptRepository;

    /**
     * giftTraceId로 해당 상품의 상태 알아내기
     * @param giftTraceId
     * @return 해당 기프티콘의 상태
     */
    public ReceiptStatus searchReceiptStatusByGiftTraceId(String giftTraceId) {
        log.debug("SEARCH RECEIPT STATUS BY GIFTTRACEID");
        // giftTraceId로 Gifticon 조회
        Gifticon gifticon = gifticonRepository.searchByGiftTraceId(giftTraceId)
                .orElseThrow(() -> new NoSuchGifticonException());
        // 조회된 Gifticon의 receiptId로 Receipt 조회
        Receipt receipt = receiptRepository.findById(gifticon.getReceipt().getId())
                .orElseThrow(() -> new NotGiftTraceIdAssociateReceiptException());
        return receipt.getReceiptStatus();
    }
}
