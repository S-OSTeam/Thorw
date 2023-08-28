package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.entity.Item;
import sosteam.throwapi.domain.order.repository.repo.ItemRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSearchService {
    private final ItemRepository itemRepository;

    public Optional<Item> getItemById(UUID itemId) {
        return itemRepository.findById(itemId);
    }

    public Set<Item> getAllItems() {
        return new HashSet<>(itemRepository.findAll());
    }
}
