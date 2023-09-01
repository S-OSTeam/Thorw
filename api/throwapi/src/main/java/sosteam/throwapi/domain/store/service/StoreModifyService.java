package sosteam.throwapi.domain.store.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.store.entity.Address;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.entity.dto.StoreModifyDto;
import sosteam.throwapi.domain.store.exception.NoSuchStoreException;
import sosteam.throwapi.domain.store.exception.WrongStoreIdException;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.store.util.GeometryUtil;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreModifyService {
    private final StoreRepository storeRepository;

    /**
     * 가게 정보를 수정하는 메소드
     * @param dto 기존 가게 코드와 가게 데이터
     * @return 변경된 가게 코드와 가게 데이터
     * 만약 기존 가게 코드가 올바르지 않으면 예외를 발생시킨다.
     *
     * 각 가게는 외부 노출 용 UUID를 가진다.
     * 사용자는 해당 가게 코드를 통해 어느 가게를 고칠것인지 알려준다.
     * 만약 가게 코드가 올바르지 않으면 데이터의 무결성이 안지켜진것이므로 수정 시도를 막는다.
     */
    @Transactional
    public StoreDto modify(StoreModifyDto dto) {
        // Find Store By given storeId
        Optional<Store> optionalStore = storeRepository.searchByExtStoreId(dto.getExtStoreId());
        if(optionalStore.isEmpty()) throw new WrongStoreIdException();
        Store store = optionalStore.get();
        store.modify(
                dto.getStoreName(),
                dto.getStorePhone(),
                dto.getCrn(),
                dto.getTrashType()
        );
        Optional<Address> optionalAddress = storeRepository.searchAddressByStore(store.getId());
        if(optionalAddress.isEmpty()) throw new NoSuchStoreException();
        Address address = optionalAddress.get();
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
        return new StoreDto(
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
