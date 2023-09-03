package sosteam.throwapi.domain.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.order.controller.request.GifticonCreateRequest;
import sosteam.throwapi.domain.order.controller.request.GifticonDeleteRequest;
import sosteam.throwapi.domain.order.controller.request.ReceiptSearchRequest;
import sosteam.throwapi.domain.order.controller.response.GifticonResponse;
import sosteam.throwapi.domain.order.controller.response.ReceiptResponse;
import sosteam.throwapi.domain.order.entity.Dto.GifticonDto;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.entity.Item;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.order.service.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
     * @param request
     * @return 아이템 정보 + LIST( 기프티콘 UUID와 giftTraceId )
     */
    @PostMapping("/purchase")
    public ResponseEntity<GifticonResponse> purchaseGifticon(@RequestBody @Valid GifticonCreateRequest request) {
        String templateToken = itemSearchService.searchTemplateTokenByProductName(request.getProductName());
        Set<GifticonResponse.GifticonInfo> gifticonInfos = new HashSet<>();

        Optional<Item> itemOptional = itemSearchService.searchItemByProductName(request.getProductName());

        if (!itemOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Item item = itemOptional.get();

        for (int i = 0; i < request.getOrderNumber(); i++) {
            Optional<Gifticon> gifticonOptional = receiptCreateService.createGifticonAndReceipt(templateToken,item);
            gifticonOptional.ifPresent(gifticon -> {
                GifticonResponse.GifticonInfo info = new GifticonResponse.GifticonInfo();
                info.setGifticonId(gifticon.getId());
                info.setGiftTraceId(gifticon.getGiftTraceId()); // Assuming Gifticon has a method getGiftTraceId
                gifticonInfos.add(info);
            });
        }

        GifticonResponse response = GifticonDto.from(item, gifticonInfos);


        return ResponseEntity.ok(response);
    }

    /**
     * 해당 User의 영수증 내역
     * @param request
     */
    @PostMapping("/search")
    public ResponseEntity<Set<ReceiptResponse>> searchReceipt(@RequestBody @Valid ReceiptSearchRequest request){
        Set<Receipt> receipts = receiptSearchService.searchUserReceipts(request.getUserId());
        Set<ReceiptResponse> responseSet = new HashSet<>();

        for (Receipt receipt : receipts) {
            Gifticon gifticon = receipt.getGifticon();
            Item item = gifticon.getItem();

            ReceiptResponse response = new ReceiptResponse();

            response.setProductName(item.getProductName());
            response.setBrandName(item.getBrandName());
            response.setBrandImageUrl(item.getBrandImageUrl());
            response.setProductImageUrl(item.getProductImageUrl());
            response.setProductThumbImageUrl(item.getProductThumbImageUrl());
            response.setPrice(item.getPrice());

            response.setGifticonId(gifticon.getId());
            response.setGiftTraceId(gifticon.getGiftTraceId());
            response.setReceiptStatus(receipt.getReceiptStatus());

            responseSet.add(response);
        }

        return ResponseEntity.ok(responseSet);
    }

    /**
     * 사용하거나 취소한 기프티콘 내역 삭제
     * @param request
     */
    @DeleteMapping
    public ResponseEntity<String> deleteReceiptAndGifticon(@RequestBody @Valid GifticonDeleteRequest request) {
        receiptDeleteService.deleteReceiptByGifticonId(request.getGiftTraceId());
        String response="Delete Receipt and Gifticon: "+request.getGiftTraceId()+
                "<" + String.valueOf(LocalDateTime.now()) + ">";
        return ResponseEntity.ok(response);
    }
}
