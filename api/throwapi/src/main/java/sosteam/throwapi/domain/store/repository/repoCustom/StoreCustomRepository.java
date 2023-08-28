package sosteam.throwapi.domain.store.repository.repoCustom;

import sosteam.throwapi.domain.store.entity.dto.StoreInRadiusDto;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;

import java.util.Optional;
import java.util.Set;

public interface StoreCustomRepository {
    Optional<Set<StoreDto>> searchStoreInRadius(StoreInRadiusDto storeInRadiusDto);

    Optional<Set<StoreDto>> searchByName(String name);

    Optional<StoreDto> searchByCRN(String crn);

}
