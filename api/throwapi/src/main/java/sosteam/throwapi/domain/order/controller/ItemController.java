package sosteam.throwapi.domain.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sosteam.throwapi.domain.order.controller.request.ItemSearchRequest;
import sosteam.throwapi.domain.order.controller.response.ItemResponse;
import sosteam.throwapi.domain.order.entity.Item;
import sosteam.throwapi.domain.order.entity.Dto.ItemDto;
import sosteam.throwapi.domain.order.service.ItemSearchService;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemSearchService itemSearchService;

    /**
     * Item 리스트 조회
     * @return 아이템 정보
     */
    @PostMapping("/search/all")
    public ResponseEntity<Set<ItemResponse>> searchItemList(){
        Set<Item> items = itemSearchService.searchAllItems();
        Set<ItemResponse> responses = items.stream()
                .map(ItemDto::toItemResponse)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(responses);
    }

    /**
     * Product 이름으로 Item 조회
     */
    @PostMapping("/search")
    public ResponseEntity<Set<ItemResponse>> searchItemByProductNameContaining(@RequestBody @Valid ItemSearchRequest request){
        Set<Item> items = itemSearchService.searchByProductNameContaining(request.getProductName())
                .orElse(Collections.emptySet());
        Set<ItemResponse> responses = items.stream()
                .map(ItemDto::toItemResponse)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(responses);
    }
}
