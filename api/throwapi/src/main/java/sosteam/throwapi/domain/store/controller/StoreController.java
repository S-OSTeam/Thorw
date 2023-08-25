package sosteam.throwapi.domain.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.store.controller.request.SearchStoreInRadiusRequest;
import sosteam.throwapi.domain.store.controller.response.SearchStoreInRadiusResponse;
import sosteam.throwapi.domain.store.controller.request.StoreSaveRequest;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.entity.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.entity.dto.SearchStoreInRadiusDto;
import sosteam.throwapi.domain.store.externalAPI.bizno.BiznoAPI;
import sosteam.throwapi.domain.store.externalAPI.bizno.BiznoApiResponse;
import sosteam.throwapi.domain.store.service.StoreCreateService;
import sosteam.throwapi.domain.store.service.StoreGetService;

import java.util.Set;
import java.util.stream.Collectors;

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
    private final BiznoAPI biznoAPI;
    @PostMapping
    public void saveStore(@RequestBody @Valid StoreSaveRequest storeSaveRequest) {
        log.debug("StoreSaveRequest = {}", storeSaveRequest);
        // Call save Service
        StoreSaveDto dto = new StoreSaveDto(
                storeSaveRequest.getName(),
                storeSaveRequest.getCompanyRegistrationNumber(),
                storeSaveRequest.getLatitude(),
                storeSaveRequest.getLongitude(),
                storeSaveRequest.getZipCode(),
                storeSaveRequest.getFullAddress()
        );
        storeCreateService.saveStore(dto);
    }

    /**
     * 현재 위치로부터 반경 내에 있는 가게들의 정보를 반환
     * @param searchStoreInRadiusRequest (위도, 경도, 반경 거리)
     * @return check down below
     */
    @GetMapping("/search")
    public Set<SearchStoreInRadiusResponse> storeSearchInRadius(@RequestBody @Valid SearchStoreInRadiusRequest searchStoreInRadiusRequest) {
        log.debug("storeSearchDto={}", searchStoreInRadiusRequest);
        // Call search Service
        SearchStoreInRadiusDto dto = new SearchStoreInRadiusDto(
                searchStoreInRadiusRequest.getLatitude(),
                searchStoreInRadiusRequest.getLongitude(),
                searchStoreInRadiusRequest.getDistance()
        );
        Set<StoreDto> storeDto = storeGetService.searchStoreInRadius(dto);
        return storeDto.stream()
                .map(store ->
                        new SearchStoreInRadiusResponse(
                                store.getName(),
                                store.getCompanyRegistrationNumber(),
                                store.getLatitude(),
                                store.getLongitude(),
                                store.getZipCode(),
                                store.getFullAddress()
                        )
                ).collect(Collectors.toSet());
    }

    /**
     * 요청온 사업자 등록 번호가 실제 국세청에 등록된 번호인지 확인하는 API
     * @param number 사업자 등록 번호
     * @return true: 등록 된 번호 , false : 등록 되지 않은 번호
     */
    @GetMapping("/companyregistrationnumber/{number}")
    public boolean confirmCompanyRegisterNumber(@PathVariable String number){
        BiznoApiResponse response = biznoAPI.confirmCompanyRegistrationNumber(number);
        boolean result = true;
        if(response == null || response.getTotalCount() == 0 || response.getResultCode() != 0)
            result = false;
        return result;
    }
}

