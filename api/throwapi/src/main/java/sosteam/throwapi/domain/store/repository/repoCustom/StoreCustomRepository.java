package sosteam.throwapi.domain.store.repository.repoCustom;

import sosteam.throwapi.domain.store.entity.dto.SearchStoreInRadiusDto;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;

import java.util.Optional;
import java.util.Set;

public interface StoreCustomRepository {
    Optional<Set<StoreDto>> search(SearchStoreInRadiusDto searchStoreInRadiusDto);

    StoreDto findByRegistrationNumber(String registrationNumber);
}
