package sosteam.throwapi.domain.store.service;

import jakarta.annotation.PostConstruct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.entity.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;

import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreGetServiceTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreGetService storeGetService;

    @Autowired
    private StoreCreateService storeCreateService;


    @DisplayName("해당 이름이 포함된 가게 검색")
    @Test
    void searchStoreByName() {
        String test = "TEST";
        String store = "STORE";

        Set<StoreDto> findTEST = storeGetService.searchStoreByName(test);
        Set<StoreDto> findSTORE = storeGetService.searchStoreByName(store);

        Assertions.assertThat(findTEST.size()).isEqualTo(findSTORE.size());
    }

    @PostConstruct
    void init2() {
        double baseLon = 126.951135;
        double baseLat = 37.543588;

        Random r = new Random();
        for (int i = 0; i < 15; i++) {
            double lat,lon;
            String name = "";
            if(i <= 4) {
                lat = baseLat + r.nextDouble() * 0.1;
                lon = baseLon + r.nextDouble() * 0.1;
                name = "TEST" + String.valueOf(i);
            } else if(i <= 9){
                lat = baseLat + r.nextDouble() * 0.1;
                lon = baseLon - r.nextDouble() * 0.1;
                name = "TESTSTORE" + String.valueOf(i);
            }
            else {
                lat = baseLat - r.nextDouble() * 0.1;
                lon = baseLon - r.nextDouble() * 0.1;
                name = "STORE" + String.valueOf(i);
            }

            String s1 =String.valueOf(r.nextInt()%1000);
            String s2 = String.valueOf(r.nextInt()%100);
            String s3 = String.valueOf(r.nextInt()%100000);
            String rn = s1 + "-" + s2 + "-" + s3;
            StoreSaveDto storeSaveDto = new StoreSaveDto(
                    name,
                    rn,
                    lon,
                    lat,
                    "test",
                    "test"
            );
            storeCreateService.saveStore(storeSaveDto);
        }
    }
}