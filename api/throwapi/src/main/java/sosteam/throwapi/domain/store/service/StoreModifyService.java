package sosteam.throwapi.domain.store.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.store.entity.Address;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.exception.InvalidRequestException;
import sosteam.throwapi.domain.store.exception.NoSuchStoreException;
import sosteam.throwapi.domain.store.exception.WrongStoreIdException;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.store.util.GeometryUtil;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.exception.NoSuchUserException;
import sosteam.throwapi.domain.user.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreModifyService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    /**
     * 가게 정보를 수정하는 메소드
     *
     * @param dto 기존 가게 코드와 가게 데이터
     * @return 변경된 가게 코드와 가게 데이터
     * 만약 기존 가게 코드가 올바르지 않으면 예외를 발생시킨다.
     * <p>
     * 각 가게는 외부 노출 용 UUID를 가진다.
     * 사용자는 해당 가게 코드를 통해 어느 가게를 고칠것인지 알려준다.
     * 만약 가게 코드가 올바르지 않으면 데이터의 무결성이 안지켜진것이므로 수정 시도를 막는다.
     */
    @Transactional
    public StoreDto modify(UUID userId, StoreDto dto) {
        // 가게 외부 UUID를 통해 해당 가게를 가져옵니다.
        Optional<Store> optionalStore = storeRepository.searchByExtStoreId(dto.getExtStoreId());
        if (optionalStore.isEmpty()) throw new WrongStoreIdException();
        Store store = optionalStore.get();

        // 불러온 가게의 주인이 수정 요청을 한 사용자와 동일 인물인지 확인합니다.
        User user = userRepository.findById(userId).orElseThrow(NoSuchUserException::new);
        UUID findUserId = storeRepository.searchUserByStore(store).orElseThrow(()-> new InvalidRequestException("MODIFY"));
        if (findUserId.compareTo(user.getId()) != 0) throw new InvalidRequestException("MODIFY");

        // 가게 정보를 수정합니다.
        store.modify(
                dto.getStoreName(),
                dto.getStorePhone(),
                dto.getCrn(),
                dto.getTrashType()
        );

        // 가게의 주소 정보를 가져옵니다.
        Optional<Address> optionalAddress = storeRepository.searchAddressByStore(store.getId());
        if (optionalAddress.isEmpty()) throw new NoSuchStoreException();
        Address address = optionalAddress.get();

        // 주소 정보를 수정합니다.
        Point location = GeometryUtil.parseLocation(
                dto.getLatitude(),
                dto.getLongitude()
        );
        address.modify(
                location,
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getFullAddress(),
                dto.getZipCode()
        );
        storeRepository.save(store);
        return new StoreDto(
                dto.getExtStoreId(),
                dto.getStoreName(),
                dto.getStorePhone(),
                dto.getCrn(),
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getZipCode(),
                dto.getFullAddress(),
                dto.getTrashType()
        );
    }
}
