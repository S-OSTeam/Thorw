package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.order.entity.ReceiptStatus;
import sosteam.throwapi.domain.order.exception.GifticonDeletionException;
import sosteam.throwapi.domain.order.exception.NoSuchGifticonException;
import sosteam.throwapi.domain.order.exception.NotGiftTraceIdAssociateReceiptException;
import sosteam.throwapi.domain.order.repository.repo.GifticonRepository;
import sosteam.throwapi.domain.order.repository.repo.ReceiptRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiptDeleteService {
    private final GifticonRepository gifticonRepository;
    private final GifticonSearchService gifticonSearchService;
    private final ReceiptRepository receiptRepository;

    private void deleteReceiptByGifticonId(UUID gifticonId) {
        log.debug("RECEIPT DELETE BY GIFTICON ID");

        Gifticon gifticon = gifticonRepository.findById(gifticonId)
                .orElseThrow(() -> new NoSuchGifticonException());

        Receipt receipt = gifticon.getReceipt();

        if (receipt == null) {
            throw new NotGiftTraceIdAssociateReceiptException();
        }

        //기프티콘 상태 확인
        if (receipt.getReceiptStatus() == ReceiptStatus.SALE) {
            throw new GifticonDeletionException();
        }

        // TODO: 2023-08-29 user 관계 끊는 코드 추가(수형이 코드 필요)

        receiptRepository.delete(receipt);
        gifticonRepository.deleteById(gifticonId);
    }
}
