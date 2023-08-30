package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.exception.CreateTokenException;
import sosteam.throwapi.domain.order.repository.repo.ItemRepository;
import sosteam.throwapi.domain.order.repository.repo.ReceiptRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiptCreateService {
    private ReceiptRepository receiptRepository;
    private ItemRepository itemRepository;

    /**
     * 상품 구매하고 카카오 기프티콘 생성(Receipt와 Gifticon 생성)
     * @param productName
     */
    public void purchaseKakaoGifticonByProductName(String productName){
        log.debug("PURCHASE KAKAO GIFTICON BY PRODUCTNAME");
        String templateToken = itemRepository.searchByProductName(productName).get().getTemplateToken();
        if(templateToken==null){
            throw new CreateTokenException();
        }
        receiptRepository.createGifticonAndReceipt(templateToken);
    }
}
