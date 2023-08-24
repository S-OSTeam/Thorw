package sosteam.throwapi.domain.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.store.dto.SearchStoreInRadiusDto;
import sosteam.throwapi.domain.store.dto.StoreResponseDto;
import sosteam.throwapi.domain.store.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.service.StoreCreateService;
import sosteam.throwapi.domain.store.service.StoreGetService;

import java.util.List;
import java.util.Set;

/**
 * 유저의 store와 관련된 컨트롤러
 * 가게 등록, 수정, 삭제가 가능하며 이미지 또한 등록이 가능하다.
 * <p>
 * 맵에서 구역을 나눠 db에 있는 섹터별 가게 정보를 보내준다.
 * 가게 정보에 있는 수거 가능한 재활용 품목을 필터로 사용한다.
 */

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreGetService storeGetService;
    private final StoreCreateService storeCreateService;
    @PostMapping
    public void save(@RequestBody @Valid StoreSaveDto storeSaveDto) {
        // Call save Service
        storeCreateService.saveStore(storeSaveDto);
    }

    /**
     * 현재 위치로부터 반경 내에 있는 가게들의 정보를 반환
     * @param searchStoreInRadiusDto (위도, 경도, 반경 거리)
     * @return check down below
     */
    @GetMapping("/search")
    public Set<StoreResponseDto> search(@RequestBody @Valid SearchStoreInRadiusDto searchStoreInRadiusDto) {
        log.debug("storeSearchDto={}", searchStoreInRadiusDto);
        // Call search Service
        return storeGetService.searchStoreInRadius(searchStoreInRadiusDto);
    }
}

