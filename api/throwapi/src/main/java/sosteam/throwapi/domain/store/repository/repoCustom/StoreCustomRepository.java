package sosteam.throwapi.domain.store.repository.repoCustom;

import sosteam.throwapi.domain.store.entity.Address;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.entity.dto.StoreInRadiusDto;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface StoreCustomRepository {
    Optional<Set<StoreDto>> searchStoreInRadius(StoreInRadiusDto storeInRadiusDto);

    Optional<Set<StoreDto>> searchByName(String name);

    Optional<StoreDto> searchByCRN(String crn);

    Optional<Store> searchByExtStoreId(UUID uuid);

    Optional<Address> searchAddressByStore(UUID uuid);
}
