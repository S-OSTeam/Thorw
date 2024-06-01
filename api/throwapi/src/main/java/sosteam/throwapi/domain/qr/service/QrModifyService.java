package sosteam.throwapi.domain.qr.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.exception.WrongStoreIdException;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.UserCngDto;
import sosteam.throwapi.domain.user.service.MileageModifyService;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class QrModifyService {
    private final StoreRepository storeRepository;
    private final MileageModifyService mileageModifyService;

    @Transactional
    public boolean qrModify(UUID extStoreId) {
        Optional<Store> optionalStore = storeRepository.searchByExtStoreId(extStoreId);
        if (optionalStore.isEmpty()) throw new WrongStoreIdException();
        Store store = optionalStore.get();
        User user = store.getUser();

        Long addMilage = Long.valueOf(10);
        mileageModifyService.modifyMileage(
                user.getInputId(),
                addMilage
        );
        return true;
    }

}
