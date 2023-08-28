package sosteam.throwapi.domain.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.store.controller.request.SearchStoreByRegistrationNumberRequest;
import sosteam.throwapi.domain.store.controller.request.SearchStoreInRadiusRequest;
import sosteam.throwapi.domain.store.controller.request.StoreSaveRequest;
import sosteam.throwapi.domain.store.controller.response.SearchStoreResponse;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.entity.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.entity.dto.SearchStoreInRadiusDto;
import sosteam.throwapi.domain.store.exception.BiznoAPIException;
import sosteam.throwapi.domain.store.exception.NoSuchRegistrationNumberException;
import sosteam.throwapi.domain.store.exception.NoSuchStoreException;
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
@Validated
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreGetService storeGetService;
    private final StoreCreateService storeCreateService;
    private final BiznoAPI biznoAPI;
    @PostMapping
    public void saveStore(@RequestBody @Valid StoreSaveRequest storeSaveRequest) {
        // Bizno RegistrationNumber Confirm API Error checking
        int resultCode = confirmCompanyRegistrationNumber(storeSaveRequest.getCompanyRegistrationNumber());
        log.debug("BIZNO API RESULT CODE ={}",resultCode);
        if(resultCode == -10) {
            throw new NoSuchRegistrationNumberException();
        } else if(resultCode < 0){
            throw new BiznoAPIException(resultCode);
        }

        // if CompanyRegistrationNumber Form is XXX-XX-XXXXX,
        // remove '-'
        // Call save Service
        StoreSaveDto dto = new StoreSaveDto(
                storeSaveRequest.getName(),
                storeSaveRequest.getCompanyRegistrationNumber().replaceAll("-",""),
                storeSaveRequest.getLatitude(),
                storeSaveRequest.getLongitude(),
                storeSaveRequest.getZipCode(),
                storeSaveRequest.getFullAddress()
        );
        log.debug("StoreSaveRequest = {}", storeSaveRequest);

        storeCreateService.saveStore(dto);
    }

    /**
     * 현재 위치로부터 반경 내에 있는 가게들의 정보를 반환
     * @param searchStoreInRadiusRequest (위도, 경도, 반경 거리)
     * @return check down below
     */
    @PostMapping("/search")
    public Set<SearchStoreResponse> storeSearchInRadius(@RequestBody @Valid SearchStoreInRadiusRequest searchStoreInRadiusRequest) {
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
                        new SearchStoreResponse(
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
     * 사업자 등록번호로 가게 조회 하기
     * @param number 사업자 등록 번호
     * @return 해당 사업자 등록 번호를 가진 가게
     * 성공 시 : 200 OK
     * 실패 (가게가 없을 시) : 404 NOT_FOUND
     * 실패 (요청으로 들어온 번호가 형식에 안맞을 경우) : 500 INTERNAL_ERROR : 서버 로그에서 확인 가능
     */
    @PostMapping("/crn")
    public SearchStoreResponse findByCompanyRegistrationNumber(@RequestBody SearchStoreByRegistrationNumberRequest number) {
        StoreDto store =  storeGetService.findByRegistrationNumber(number.getCompanyRegistrationNumber().replaceAll("-",""));
        if(store == null) throw new NoSuchStoreException();
        return new SearchStoreResponse(
                store.getName(),
                store.getCompanyRegistrationNumber(),
                store.getLatitude(),
                store.getLongitude(),
                store.getZipCode(),
                store.getFullAddress()
        );
    }


    /**
     * BIZNO API를 이용하여 해당 사업자 번호가 국세청에 등록된 번호인지 확인
     * @param number 사업자 등록 번호
     * @return resultCode
     *  -1 : 미등록 사용자 -> Wrong API-KEY
     *  -2 : 파라메터 오류
     *  -3 : 1일 100건 조회수 초과
     *  9 : 기타 오류
     *  -10 : 해당 번호 존재 X
     */
    public int confirmCompanyRegistrationNumber(String number){
        BiznoApiResponse response = biznoAPI.confirmCompanyRegistrationNumber(number);
        int resultCode;
        if(response == null || response.getTotalCount() == 0) resultCode = -10;
        else resultCode = response.getResultCode();
        return resultCode;
    }
}

