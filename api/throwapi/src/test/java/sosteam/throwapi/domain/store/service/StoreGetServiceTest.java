package sosteam.throwapi.domain.store.service;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sosteam.throwapi.domain.store.dto.StoreResponseDto;
import sosteam.throwapi.domain.store.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.dto.StoreSearchDto;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreGetServiceTest {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreGetService storeGetService;
    @Autowired
    private StoreCreateService storeCreateService;
    @Test
    void search() {
        StoreSearchDto storeSearchDto = new StoreSearchDto(
                37.543588,
                126.951135,
                1.0,
                null
        );
        List<StoreResponseDto> stores = storeGetService.search(storeSearchDto);
        for (StoreResponseDto store : stores) {
            System.out.println("store = " + store.toString());
        }
    }

    @PostConstruct
    void init() {
        double baseLon = 126.951135;
        double baseLat = 37.543588;

        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            double lat,lon;
            if(i <= 250) {
                lat = baseLat + r.nextDouble() * 0.01;
                lon = baseLon + r.nextDouble() * 0.01;
            } else if(i <= 500) {
                lat = baseLat + r.nextDouble() * 0.01;
                lon = baseLon - r.nextDouble() * 0.01;
            } else if(i <= 750) {
                lat = baseLat - r.nextDouble() * 0.01;
                lon = baseLon + r.nextDouble() * 0.01;
            } else {
                lat = baseLat - r.nextDouble() * 0.01;
                lon = baseLon - r.nextDouble() * 0.01;
            }
            StoreSaveDto storeSaveDto = new StoreSaveDto(
                    "TestStore" + String.valueOf(i),
                    String.valueOf(r.nextInt()),
                    "test",
                    lon,
                    lat,
                    "test",
                    "test"
            );
            storeCreateService.save(storeSaveDto);
        }
    }
}