package sosteam.throwapi.domain.store.controller;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import sosteam.throwapi.domain.store.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.service.StoreCreateService;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 테스트 아님 돌리지마삼
 */
class StoreControllerTest {
    private StoreCreateService storeCreateService;

    @Test
    void search() {
    }

    @PostConstruct
    void init2() {
        double baseLon = 126.951135;
        double baseLat = 37.543588;

        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            double lat,lon;
            if(i <= 250) {
                lat = baseLat + r.nextDouble() * 0.1;
                lon = baseLon + r.nextDouble() * 0.1;
            } else if(i <= 500) {
                lat = baseLat + r.nextDouble() * 0.1;
                lon = baseLon - r.nextDouble() * 0.1;
            } else if(i <= 750) {
                lat = baseLat - r.nextDouble() * 0.1;
                lon = baseLon + r.nextDouble() * 0.1;
            } else {
                lat = baseLat - r.nextDouble() * 0.1;
                lon = baseLon - r.nextDouble() * 0.1;
            }

            String s1 =String.valueOf(r.nextInt()%1000);
            String s2 = String.valueOf(r.nextInt()%100);
            String s3 = String.valueOf(r.nextInt()%100000);
            String rn = s1 + "-" + s2 + "-" + s3;
            StoreSaveDto storeSaveDto = new StoreSaveDto(
                    "TestStore" + String.valueOf(i),
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