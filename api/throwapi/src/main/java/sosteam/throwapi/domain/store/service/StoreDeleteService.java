package sosteam.throwapi.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.exception.InvalidRequestException;
import sosteam.throwapi.domain.store.exception.NoSuchStoreException;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreDeleteService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    public UUID deleteStore(UUID userId, UUID extStoreId) {
        // 가게 외부 UUID로 해당 가게를 불러옵니다.
        Optional<Store> optionalStore = storeRepository.searchByExtStoreId(extStoreId);
        if (optionalStore.isEmpty()) throw new NoSuchStoreException();
        Store store = optionalStore.get();

        // 삭제를 요청한 사용자와 해당 가게의 주인이 동일 인물인지 확인합니다.
        User user = userRepository.findById(userId).orElseThrow(NoSuchStoreException::new);
        UUID findUserId = storeRepository.searchUserByStore(store).orElseThrow(()-> new InvalidRequestException("DELETE"));
        if (findUserId.compareTo(user.getId()) != 0) throw new InvalidRequestException("DELETE");

        if (
                store.getExtStoreId().equals(extStoreId)
        ) {
            // 가게와 주소의 연관관계를 끊어줍니다.
            storeRepository.delete(store);
        } else throw new InvalidRequestException("DELETE");

        return extStoreId;
    }
}
