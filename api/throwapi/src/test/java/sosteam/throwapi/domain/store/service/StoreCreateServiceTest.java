package sosteam.throwapi.domain.store.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sosteam.throwapi.domain.store.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreCreateServiceTest {
    @Autowired
    private StoreCreateService storeCreateService;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void save(){
        StoreSaveDto storeSaveDto = new StoreSaveDto(
                "시실리",
                "1234",
                "1234",
                126.969896,
                37.530975,
                "1234",
                "1234"
        );
        Store store = storeCreateService.save(storeSaveDto);

        UUID uuid = store.getId();
        Store findStore = storeRepository.findById(uuid).get();

        Assertions.assertThat(findStore).isEqualTo(store);
    }
}