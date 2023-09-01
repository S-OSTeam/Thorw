package sosteam.throwapi.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.store.entity.Address;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.exception.InvalidDeleteException;
import sosteam.throwapi.domain.store.exception.NoSuchStoreException;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreDeleteService {
    private final StoreRepository storeRepository;


    public void deleteStore(UUID extStoreId) {
        Optional<Store> optionalStore = storeRepository.searchByExtStoreId(extStoreId);
        if(optionalStore.isEmpty()) throw new NoSuchStoreException();
        Store store = optionalStore.get();
        Optional<Address> optionalAddress = storeRepository.searchAddressByStore(store.getId());
        if(optionalAddress.isEmpty()) throw new NoSuchStoreException();
        Address address = optionalAddress.get();
        // 요청이 들어온 데이터와 삭제하고자하는 데이터가 전부 일치해야한다.
        if(
           store.getExtStoreId().equals(extStoreId)
        ) {
            store.modifyAddress(null);
            address.modifyStore(null);
            storeRepository.delete(store);
        } else throw new InvalidDeleteException();
    }
}
