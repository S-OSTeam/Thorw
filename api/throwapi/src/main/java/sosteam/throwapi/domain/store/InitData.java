package sosteam.throwapi.domain.store;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sosteam.throwapi.domain.store.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.service.StoreCreateService;

import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitData {
    private final StoreCreateService storeCreateService;

    @PostConstruct
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
