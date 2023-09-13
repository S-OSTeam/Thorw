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
import sosteam.throwapi.domain.user.exception.NoSuchUserException;
import sosteam.throwapi.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreCreateService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Store saveStore(UUID userId,StoreSaveDto storeDto) {
        log.debug("가게 저장 서비스 시작 = {}", storeDto);
        // 만약에 해당 사업자 번호로 등록된 가게가 존재할 경우,
        Optional<StoreDto> result = storeRepository.searchByCRN(storeDto.getCrn());
        // 409 충돌 에러를 반환 합니다.
        if (result.isPresent()) throw new StoreAlreadyExistException();
        // 가게 외부 노출용 UUID를 생성합니다.
        UUID extStoreId = UUID.randomUUID();
        // 1. 가게 객체 생성
        Store store = new Store(
                extStoreId,
                storeDto.getStoreName(),
                storeDto.getStorePhone(),
                storeDto.getCrn(),
                storeDto.getTrashType()
        );

        // 2: 주소 객체 생성
        // 2-1 : 위도 경도를 통해 POINT 타입 멤버를 만듭니다.
        Point location = GeometryUtil.parseLocation(
                storeDto.getLatitude(),
                storeDto.getLongitude()
        );
        Address address = new Address(
                location,
                storeDto.getLatitude(),
                storeDto.getLongitude(),
                storeDto.getFullAddress(),
                storeDto.getZipCode()
        );

        // 4: 가게와 주소 객체를 연결 합니다.
        address.modifyStore(store);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        store.modifyAddress(addresses);

        // 사용자 불러오기
        User user = userRepository.findById(userId).orElseThrow(NoSuchUserException::new);

        // 5: 가게와 사용자 객체를 연결 합니다.
        store.modifyUser(user);
        List<Store> stores = user.getStores();
        if(stores == null) {
            stores = new ArrayList<>();
            user.modifyStore(stores);
        }
        stores.add(store);

        // 6: 가게 객체를 저장 합니다.
        return storeRepository.save(store);
    }
}
