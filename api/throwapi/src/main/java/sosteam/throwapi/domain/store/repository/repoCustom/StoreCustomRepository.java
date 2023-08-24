package sosteam.throwapi.domain.store.repository.repoCustom;

import sosteam.throwapi.domain.store.dto.StoreResponseDto;
import sosteam.throwapi.domain.store.dto.SearchStoreInRadiusDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StoreCustomRepository {
    Optional<Set<StoreResponseDto>> search(SearchStoreInRadiusDto searchStoreInRadiusDto);

    StoreResponseDto findByRegistrationNumber(String registrationNumber);
}
