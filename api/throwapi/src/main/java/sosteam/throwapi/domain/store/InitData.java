package sosteam.throwapi.domain.store;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sosteam.throwapi.domain.store.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.service.StoreCreateService;

import java.util.Random;

@Slf4j
//@Component
@RequiredArgsConstructor
public class InitData {
    private final StoreCreateService storeCreateService;

    //@PostConstruct
    void init() {
        double baseLon = 126.951135;
        double baseLat = 37.543588;

        Random r = new Random();
        for (int i = 0; i < 100000; i++) {
            double lat,lon;
            if(i <= 25000) {
                lat = baseLat + r.nextDouble() * 0.1;
                lon = baseLon + r.nextDouble() * 0.1;
            } else if(i <= 50000) {
                lat = baseLat + r.nextDouble() * 0.1;
                lon = baseLon - r.nextDouble() * 0.1;
            } else if(i <= 75000) {
                lat = baseLat - r.nextDouble() * 0.1;
                lon = baseLon + r.nextDouble() * 0.1;
            } else {
                lat = baseLat - r.nextDouble() * 0.1;
                lon = baseLon - r.nextDouble() * 0.1;
            }
            StoreSaveDto storeSaveDto = new StoreSaveDto(
                    "TestStore" + String.valueOf(i),
                    String.valueOf(r.nextInt()%1000)+"-"+String.valueOf(r.nextInt()%100) + "-" + String.valueOf(r.nextInt()%100000),
                    lon,
                    lat,
                    "test",
                    "test"
            );
            storeCreateService.save(storeSaveDto);
        }
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
            storeCreateService.save(storeSaveDto);
        }
    }
}
