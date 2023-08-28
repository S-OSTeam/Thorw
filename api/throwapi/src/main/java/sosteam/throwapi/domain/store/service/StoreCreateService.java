package sosteam.throwapi.domain.store.service;

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

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreCreateService {
    private final StoreRepository storeRepository;
    private final StoreGetService storeGetService;
    public Store saveStore(StoreSaveDto storeSaveDto) {
        log.debug("Start Creating Store = {}", storeSaveDto);
        // if Store is already Exist
        // return 409 : Conflict http status
        Optional<StoreDto> storeDto = storeGetService.isExistByCRN(storeSaveDto.getCrn());
        if (storeDto.isPresent()) throw new StoreAlreadyExistException();

        // 1: Create Store Entity
        Store store = new Store(
                storeSaveDto.getName(),
                storeSaveDto.getCrn()
        );

        // 2: Create Address Entity
        // 2-1 : Create Point based on Lat,Lon
        Point location = GeometryUtil.parseLocation(
                storeSaveDto.getLatitude(),
                storeSaveDto.getLongitude()
        );
        log.debug("location={}", location);
        Address address = new Address(
                location,
                storeSaveDto.getLatitude(),
                storeSaveDto.getLongitude(),
                storeSaveDto.getFullAddress(),
                storeSaveDto.getZipCode()
        );

        // 3: Mapping Store and Address
        store.modifyAddress(address);
        address.modifyStore(store);

        // 4: save Store Entity
        return storeRepository.save(store);
    }
}
