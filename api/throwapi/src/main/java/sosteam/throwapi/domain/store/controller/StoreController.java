package sosteam.throwapi.domain.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sosteam.throwapi.domain.store.controller.request.*;
import sosteam.throwapi.domain.store.controller.response.StoreResponse;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.entity.dto.StoreInRadiusDto;
import sosteam.throwapi.domain.store.entity.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.service.*;
import sosteam.throwapi.domain.user.entity.dto.user.UserInfoDto;
import sosteam.throwapi.domain.user.service.UserSeaerchService;
import sosteam.throwapi.global.service.JwtTokenService;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
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
    private final StoreSearchService storeSearchService;
    private final StoreCreateService storeCreateService;
    private final StoreModifyService storeModifyService;
    private final StoreDeleteService storeDeleteService;
    private final BiznoService biznoService;
    private final JwtTokenService jwtTokenService;
    private final UserSeaerchService userSearchService;

    /**
     * 새로운 가게를 등록합니다.
     * @param auth 사용자의 토큰
     * @param request 생성할 가게 정보
     * @return 가게의 외부 UUID
     */
    @PostMapping
    public ResponseEntity<UUID> saveStore(
            @RequestHeader(name = "Authorization", required = true)
            String auth,
            @RequestBody @Valid StoreSaveRequest request
    ) {
        // 비즈노 API 호출 성공 시, 해당 사업자 번호의 가게 이름을 가져옵니다.
        String accessToken = auth.split(" ")[1];
        String storeName = biznoService.confirmCompanyRegistrationNumber(request.getCrn(), accessToken);

        // Bearer qwee..가 acdessToken이므로 실제 토큰인 뒷 부분을 가져옵니다.
        UUID userId = getUserByToken(accessToken);

        // 사업자 번호 입력 시 "-" 를 제거합니다
        String crn = request.getCrn().replaceAll("-", "");
        StoreSaveDto dto = new StoreSaveDto(
                null,
                storeName,
                request.getStorePhone(),
                crn,
                request.getLatitude(),
                request.getLongitude(),
                request.getZipCode(),
                request.getFullAddress(),
                request.getTrashType()
        );
        Store store = storeCreateService.saveStore(userId,dto);
        return ResponseEntity.ok(store.getExtStoreId());
    }

    /**
     * 내 가게 불러오기
     * @param auth 사용자 토큰
     * @return 해당 사용자의 가게들을 반환
     */
    @GetMapping("/user")
    public ResponseEntity<Set<StoreResponse>> searchMyStores(
            @RequestHeader(name = "Authorization", required = true)
            String auth
    ) {
        String accessToken = auth.split(" ")[1];
        UUID userId = getUserByToken(accessToken);
        Set<StoreDto> storeDtos = storeSearchService.searchMyStores(userId);
        Set<StoreResponse> resp = storeDtos.stream().map(StoreDto::toResponse).collect(Collectors.toSet());
        return ResponseEntity.ok(resp);
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
        // 필터링 기능 추가
        // 0을 _로 바꾼 뒤 각 쓰레기 종류를 제공하는 가게들을 찾늗다.
        // ex) 10110 -> like "1_11_"
        StoreInRadiusDto dto = new StoreInRadiusDto(
                request.getLatitude(),
                request.getLongitude(),
                request.getDistance(),
                request.getTrashType().replace("0","_")
        );

        // Convert Dto to Response
        Set<StoreDto> storeDtos = storeSearchService.searchStoreInRadius(dto);
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
        StoreDto dto =  storeSearchService.searchByCRN(request.getCrn().replaceAll("-",""));
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
        Set<StoreDto> dtos = storeSearchService.searchStoreByName(request.getStoreName());
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
    public ResponseEntity<StoreResponse> modifyStore(
            @RequestHeader(name = "Authorization", required = true)
            String auth,
            @RequestBody @Valid StoreModifyRequest request
    ) {
        // Bearer qwee..가 acdessToken이므로 실제 토큰인 뒷 부분을 가져옵니다.
        String accessToken = auth.split(" ")[1];
        UUID userId = getUserByToken(accessToken);

        // 비즈노 호출 성공 시 가게 이름을 받는다.
        String storeName = biznoService.confirmCompanyRegistrationNumber(request.getCrn(), accessToken);

        // 사업자 번호 입력 시 "-" 를 제거합니다
        String crn = request.getCrn().replaceAll("-", "");

        // 수정 데이터가 담긴 dto를 생성합니다.
        StoreDto dto = new StoreDto(
                request.getExtStoreId(),
                storeName,
                request.getStorePhone(),
                crn,
                request.getLatitude(),
                request.getLongitude(),
                request.getZipCode(),
                request.getFullAddress(),
                request.getTrashType()
        );

        // 수정 서비스를 호출합니다.
        StoreDto storeDto = storeModifyService.modify(userId,dto);

        // 수정이 완료되었다면, 수정 된 가게 데이터를 반환합니다.
        return ResponseEntity.ok(storeDto.toResponse());
    }

    /**
     * 요청 가게를 삭제합니다.
     * @param auth 요청한 사용자 토큰
     * @param request 삭제를 원하는 가게의 외부 UUID
     * @return 삭제 완료 문구
     */
    @DeleteMapping
    public ResponseEntity<String> deleteStore(
            @RequestHeader(name = "Authorization", required = true)
            String auth,
            @RequestBody @Valid StoreDeleteRequest request
    ) {
        // Bearer qwee..가 acdessToken이므로 실제 토큰인 뒷 부분을 가져옵니다.
        String accessToken = auth.split(" ")[1];
        UUID userId = getUserByToken(accessToken);

        // 가게 삭제 서비스 호출
        storeDeleteService.deleteStore(userId,request.getExtStoreId());

        // 삭제 서비스가 완료되면 삭제 된 가게의 외부 UUID와 삭제 된 시간을 문자열로 반환합니다.
        String resp = "Delete Store: " + request.getExtStoreId() +
                "<" + String.valueOf(LocalDateTime.now()) + ">";
        return ResponseEntity.ok(resp);
    }

    // 토큰을 통해 사용자를 가져 옵니다.
    public UUID getUserByToken(String token) {
        UserInfoDto userInfoDto = new UserInfoDto(
                jwtTokenService.extractSubject(token)
        );
        return userSearchService.searchByInputId(userInfoDto).getId();
    }
}

