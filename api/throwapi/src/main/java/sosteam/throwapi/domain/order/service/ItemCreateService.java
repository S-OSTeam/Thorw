package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sosteam.throwapi.domain.order.entity.Item;
import sosteam.throwapi.domain.order.repository.repo.ItemRepository;


@Slf4j
@Service
@RequiredArgsConstructor
public class ItemCreateService {
    private final ItemRepository itemRepository;

    @Transactional
    public Item createItem(Item item) {
        log.debug("ITEM CREATE");
        return itemRepository.save(item);
    }
}
