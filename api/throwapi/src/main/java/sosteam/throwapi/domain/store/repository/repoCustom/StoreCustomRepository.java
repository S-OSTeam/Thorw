package sosteam.throwapi.domain.store.repository.repoCustom;

import sosteam.throwapi.domain.store.dto.StoreResponseDto;
import sosteam.throwapi.domain.store.dto.StoreSearchDto;

import java.util.List;
import java.util.Optional;

public interface StoreCustomRepository {
    Optional<List<StoreResponseDto>> search(StoreSearchDto storeSearchDto);
}
