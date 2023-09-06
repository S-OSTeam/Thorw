package sosteam.throwapi.domain.store.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.store.entity.Address;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.entity.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.exception.StoreAlreadyExistException;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.store.util.GeometryUtil;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.UserInfoDto;
import sosteam.throwapi.domain.user.service.UserInfoService;
import sosteam.throwapi.global.service.JwtTokenService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreCreateService {
    private final StoreRepository storeRepository;
    private final StoreGetService storeGetService;

    private final UserInfoService userInfoService;
    private final JwtTokenService jwtTokenService;
    //@DistributeLock(key="#storeDto.getAccessToken()")
    @Transactional
    public Store saveStore(StoreSaveDto storeDto) {
        log.debug("Start Creating Store = {}", storeDto);
        // if Store is already Exist
        // return 409 : Conflict http status
        Optional<StoreDto> result = storeGetService.isExistByCRN(storeDto.getCrn());
        if (result.isPresent()) throw new StoreAlreadyExistException();


        // 요청 사용자 정보 가져오기
        UserInfoDto userInfoDto = new UserInfoDto(
                jwtTokenService.extractSubject(storeDto.getAccessToken())
        );
        User user = userInfoService.searchByInputId(userInfoDto);

        // Make external Store id using UUID
        UUID extStoreId = UUID.randomUUID();
        // 1: Create Store Entity
        Store store = new Store(
                extStoreId,
                storeDto.getStoreName(),
                storeDto.getStorePhone(),
                storeDto.getCrn(),
                storeDto.getTrashType()
        );

        // 2: Create Address Entity
        // 2-1 : Create Point based on Lat,Lon
        Point location = GeometryUtil.parseLocation(
                storeDto.getLatitude(),
                storeDto.getLongitude()
        );
        log.debug("location={}", location);
        Address address = new Address(
                location,
                storeDto.getLatitude(),
                storeDto.getLongitude(),
                storeDto.getFullAddress(),
                storeDto.getZipCode()
        );

        // 4: Mapping Store and Address
        store.modifyAddress(address);
        address.modifyStore(store);

        // 5: Mapping Store and User
        store.modifyUser(user);
        List<Store> stores = user.getStores();
        stores.add(store);
        user.modifyStore(stores);

        // 6: save Store Entity
        return storeRepository.save(store);
    }
}
