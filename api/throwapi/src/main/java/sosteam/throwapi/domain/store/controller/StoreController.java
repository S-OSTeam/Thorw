package sosteam.throwapi.domain.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.store.controller.request.*;
import sosteam.throwapi.domain.store.controller.response.StoreModifyResponse;
import sosteam.throwapi.domain.store.controller.response.StoreResponse;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.entity.dto.StoreInRadiusDto;
import sosteam.throwapi.domain.store.entity.dto.StoreModifyDto;
import sosteam.throwapi.domain.store.exception.BiznoAPIException;
import sosteam.throwapi.domain.store.exception.NoSuchRegistrationNumberException;
import sosteam.throwapi.domain.store.exception.NoSuchStoreException;
import sosteam.throwapi.domain.store.externalAPI.bizno.BiznoAPI;
import sosteam.throwapi.domain.store.externalAPI.bizno.BiznoApiResponse;
import sosteam.throwapi.domain.store.service.StoreCreateService;
import sosteam.throwapi.domain.store.service.StoreGetService;
import sosteam.throwapi.domain.store.service.StoreModifyService;
import sosteam.throwapi.domain.store.util.SHA256;

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
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreGetService storeGetService;
    private final StoreCreateService storeCreateService;
    private final StoreModifyService storeModifyService;
    private final BiznoAPI biznoAPI;
    @PostMapping
    public void saveStore(@RequestBody @Valid StoreSaveRequest request) {
        // Bizno RegistrationNumber Confirm API Error checking
        String storeName = confirmCompanyRegistrationNumber(request.getCrn());
        log.info("POST : BIZNO API RESULT : StoreName ={}",storeName);
        // if CompanyRegistrationNumber Form is XXX-XX-XXXXX,
        // remove '-'
        // Call save Service
        StoreDto dto = new StoreDto(
                storeName,
                request.getStorePhone(),
                request.getCrn().replaceAll("-",""),
                request.getLatitude(),
                request.getLongitude(),
                request.getZipCode(),
                request.getFullAddress()
        );
        log.debug("StoreSaveRequest = {}", request);

        storeCreateService.saveStore(dto);
    }

    /**
     * 현재 위치로부터 반경 내에 있는 가게들의 정보를 반환
     * @param request (위도, 경도, 반경 거리)
     * @return check down below
     */
    @PostMapping("/search")
    public ResponseEntity<Set<StoreResponse>> searchStoreInRadius(@RequestBody @Valid StoreInRadiusRequest request) {
        log.debug("storeSearchDto={}", request);
        // Call search Service
        StoreInRadiusDto dto = new StoreInRadiusDto(
                request.getLatitude(),
                request.getLongitude(),
                request.getDistance()
        );
        // Convert Dto to Response
        Set<StoreDto> storeDtos = storeGetService.searchStoreInRadius(dto);
        Set<StoreResponse> resp = storeDtos.stream().map(StoreDto::toResponse).collect(Collectors.toSet());
        return ResponseEntity.ok(resp);
    }

    /**
     * 사업자 등록번호로 가게 조회 하기
     * @param request 사업자 등록 번호
     * @return 해당 사업자 등록 번호를 가진 가게
     * 성공 시 : 200 OK
     * 실패 (가게가 없을 시) : 404 NOT_FOUND
     * 실패 (요청으로 들어온 번호가 형식에 안맞을 경우) : 500 INTERNAL_ERROR : 서버 로그에서 확인 가능
     */
    @PostMapping("/crn")
    public ResponseEntity<StoreResponse> searchByCompanyRegistrationNumber(@RequestBody @Valid StoreCrnRequest request) {
        StoreDto dto =  storeGetService.searchByCRN(request.getCrn().replaceAll("-",""));
        if(dto == null) throw new NoSuchStoreException();
        StoreResponse resp = dto.toResponse();
        return ResponseEntity.ok(resp);
    }


    /**
     * 검색 이름이 포함된 가게들의 정보를 반환
     * @param request 가게이름
     * @return 가게 정보들
     */
    @PostMapping("/name")
    public ResponseEntity<Set<StoreResponse>> searchByName(@RequestBody @Valid StoreNameRequest request) {
        Set<StoreDto> dtos = storeGetService.searchStoreByName(request.getStoreName());
        Set<StoreResponse> resp = dtos.stream().map(StoreDto::toResponse).collect(Collectors.toSet());
        return ResponseEntity.ok(resp);
    }

    /**
     * 가게 수정 메소드
     * @param request 가게코드, 가게 정보
     * @return 가게코드, 가게 정보
     * 가게 코드는 가게 정보들로 이루어진 암호문
     */
    @PutMapping
    public ResponseEntity<StoreModifyResponse> modifyStore(@RequestBody @Valid StoreModifyRequest request) {
        // Bizno RegistrationNumber Confirm API Error checking
        String storeName = confirmCompanyRegistrationNumber(request.getCrn());
        log.info("PUT: BIZNO API RESULT : StoreName ={}",storeName);

        // if CompanyRegistrationNumber Form is XXX-XX-XXXXX,
        // remove '-'
        StoreModifyDto dto = new StoreModifyDto(
                request.getStoreCode(),
                storeName,
                request.getStorePhone(),
                request.getCrn().replaceAll("-",""),
                request.getLatitude(),
                request.getLongitude(),
                request.getZipCode(),
                request.getFullAddress()
        );
        // Call modify Method
        log.debug("StoreModifyRequest = {}", dto);
        StoreDto storeDto = storeModifyService.modify(dto);

        // Create Response
        StoreModifyResponse resp = new StoreModifyResponse(
                SHA256.encrypt(storeDto.concat()),
                storeDto.getStoreName(),
                storeDto.getStorePhone(),
                storeDto.getCrn(),
                storeDto.getLatitude(),
                storeDto.getLongitude(),
                storeDto.getZipCode(),
                storeDto.getFullAddress()
        );
        return ResponseEntity.ok(resp);
    }

    /**
     * BIZNO API를 이용하여 해당 사업자 번호가 국세청에 등록된 번호인지 확인
     * @param number 사업자 등록 번호
     * @return result 사업자 등록 번호로 등록된 가게 이름 -> storeName
     * response.getResultCode() :
     *  -1 : 미등록 사용자 -> Wrong API-KEY
     *  -2 : 파라메터 오류
     *  -3 : 1일 100건 조회수 초과
     *  9 : 기타 오류
     *  -10 : 해당 번호 존재 X
     */
    public String confirmCompanyRegistrationNumber(String number){
        BiznoApiResponse response = biznoAPI.confirmCompanyRegistrationNumber(number);
        if( response == null || response.getTotalCount() == 0) throw new NoSuchRegistrationNumberException();
        if(response.getResultCode() < 0) throw new BiznoAPIException(response.getResultCode());
        return response.getItems().get(0).getCompany();
    }
}

