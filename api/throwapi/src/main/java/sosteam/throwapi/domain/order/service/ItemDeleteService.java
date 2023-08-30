package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.repository.repo.ItemRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemDeleteService {
    private final ItemRepository itemRepository;

    public void deleteItem(UUID itemId) {
        log.debug("ITEM DELETE");
        itemRepository.deleteById(itemId);
    }
}
