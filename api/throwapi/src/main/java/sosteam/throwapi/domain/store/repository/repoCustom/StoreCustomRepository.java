package sosteam.throwapi.domain.store.repository.repoCustom;

import sosteam.throwapi.domain.store.dto.StoreResponseDto;
import sosteam.throwapi.domain.store.dto.SearchStoreInRadiusDto;

import java.util.List;
import java.util.Optional;

public interface StoreCustomRepository {
    Optional<List<StoreResponseDto>> search(SearchStoreInRadiusDto searchStoreInRadiusDto);

    StoreResponseDto findByRegistrationNumber(String registrationNumber);
}
