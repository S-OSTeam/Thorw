package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.entity.Item;
import sosteam.throwapi.domain.order.exception.CreateTokenException;
import sosteam.throwapi.domain.order.exception.NoSuchItemException;
import sosteam.throwapi.domain.order.repository.repo.ItemRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSearchService {
    private final ItemRepository itemRepository;

    /**
     * productName으로 TemplateToken 찾기
     * @param productName
     */
    public String searchTTByProductName(String productName){
        log.debug("PURCHASE KAKAO GIFTICON BY PRODUCTNAME");
        Optional<Item> optionalItem = itemRepository.searchByProductName(productName);

        if (!optionalItem.isPresent()) {
            throw new NoSuchItemException();
        }

        String templateToken = optionalItem.get().getTemplateToken();
        if (templateToken == null) {
            throw new CreateTokenException();
        }

        return templateToken;
    }

    public Set<Item> searchAllItems() {
        log.debug("ITEMS SEARCH");
        return new HashSet<>(itemRepository.findAll());
    }

    /**
     * 상품 이름으로 아이템들 찾기(user에게 제공해줄 것)
     * @param productName
     */
    public Optional<Set<Item>> searchByProductNameContaining(String productName) {
        log.debug("SEARCH ITEMS CONTAINING PRODUCT NAME");
        return itemRepository.searchByProductNameContaining(productName);
    }

    /**
     * 상품 이름으로 아이템 찾기
     * @param productName
     */
    public Optional<Item> searchItemByProductName(String productName) {
        log.debug("SEARCH ITEM BY PRODUCT NAME");
        return itemRepository.searchByProductName(productName);
    }
}
