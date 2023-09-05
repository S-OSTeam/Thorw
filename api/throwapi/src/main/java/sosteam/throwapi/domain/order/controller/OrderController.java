package sosteam.throwapi.domain.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.order.controller.request.GifticonCreateRequest;
import sosteam.throwapi.domain.order.controller.request.GifticonDeleteRequest;
import sosteam.throwapi.domain.order.controller.request.ReceiptSearchRequest;
import sosteam.throwapi.domain.order.controller.response.GifticonResponse;
import sosteam.throwapi.domain.order.controller.response.ReceiptResponse;
import sosteam.throwapi.domain.order.entity.Dto.GifticonDto;
import sosteam.throwapi.domain.order.entity.Dto.ReceiptDto;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.entity.Item;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.order.exception.NoSuchGifticonException;
import sosteam.throwapi.domain.order.exception.NoSuchItemException;
import sosteam.throwapi.domain.order.service.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final GifticonSearchService gifticonSearchService;
    private final ItemSearchService itemSearchService;
    private final ReceiptCreateService receiptCreateService;
    private final ReceiptDeleteService receiptDeleteService;
    private final ReceiptSearchService receiptSearchService;

    /**
     * 기프티콘 구매
     *
     * @param request
     * @return 아이템 정보 + LIST( 기프티콘 UUID와 giftTraceId )
     */
    @PostMapping("/purchase")
    public ResponseEntity<GifticonResponse> purchaseGifticon(@RequestBody @Valid GifticonCreateRequest request) {
        String templateToken = itemSearchService.searchTemplateTokenByProductName(request.getProductName());
        Optional<Item> itemOptional = itemSearchService.searchItemByProductName(request.getProductName());

        if (!itemOptional.isPresent()) {
            throw new NoSuchItemException();
        }

        Item item = itemOptional.get();

        Optional<Gifticon> gifticonOptional = receiptCreateService.createGifticonAndReceipt(templateToken, item);

        // 생성된 Gifticon이 없을 경우, 서버 에러 응답을 반환
        if (!gifticonOptional.isPresent()) {
            throw new NoSuchGifticonException();
        }

        Gifticon gifticon = gifticonOptional.get();

        // GifticonResponse로 변환
        GifticonResponse response = GifticonDto.from(item,gifticon);

        return ResponseEntity.ok(response);
    }

    /**
     * 해당 User의 영수증 내역
     * @param request
     */
    @PostMapping("/search")
    public ResponseEntity<Set<ReceiptResponse>> searchReceipt(@RequestBody @Valid ReceiptSearchRequest request) {
        Set<Receipt> receipts = receiptSearchService.searchUserReceipts(request.getUserId());
        Set<ReceiptResponse> responseSet = getReceiptResponses(receipts);

        return ResponseEntity.ok(responseSet);
    }

    private static Set<ReceiptResponse> getReceiptResponses(Set<Receipt> receipts) {
        Set<ReceiptResponse> responseSet = receipts.stream()
                .map(receipt -> {
                    Gifticon gifticon = receipt.getGifticon();
                    Item item = gifticon.getItem();
                    ReceiptResponse response = ReceiptDto.from(item, gifticon);
                    response.setReceiptStatus(receipt.getReceiptStatus());
                    return response;
                })
                .collect(Collectors.toSet());
        return responseSet;
    }

    /**
     * 사용하거나 취소한 기프티콘 내역 삭제
     *
     * @param request
     */
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteReceiptAndGifticon(@RequestBody @Valid GifticonDeleteRequest request) {
        receiptDeleteService.deleteReceiptByGifticonId(request.getGiftTraceId());
        Map<String, Object> response = new HashMap<>();
        response.put("giftTraceId", request.getGiftTraceId());
        response.put("deletedAt", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
