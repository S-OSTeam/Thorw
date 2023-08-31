package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.entity.Item;
import sosteam.throwapi.domain.order.exception.CreateTokenException;
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
    public String searchTemplateTokenByProductName(String productName){
        log.debug("PURCHASE KAKAO GIFTICON BY PRODUCTNAME");
        String templateToken = null;
        templateToken = itemRepository.searchByProductName(productName).get().getTemplateToken();
        if(templateToken==null){
            throw new CreateTokenException();
        }
        return templateToken;
    }

    public Optional<Item> searchItemByProductName(String productName) {
        log.debug("SEARCH ITEM BY PRODUCT NAME");
        return itemRepository.searchByProductName(productName);
    }

    public Set<Item> searchAllItems() {
        log.debug("ITEMS SEARCH");
        return new HashSet<>(itemRepository.findAll());
    }
}
