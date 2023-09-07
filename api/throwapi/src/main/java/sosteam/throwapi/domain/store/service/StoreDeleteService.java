package sosteam.throwapi.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.store.entity.Address;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.exception.InvalidRequestException;
import sosteam.throwapi.domain.store.exception.NoSuchStoreException;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.UserInfoDto;
import sosteam.throwapi.domain.user.service.UserSeaerchService;
import sosteam.throwapi.global.service.JwtTokenService;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreDeleteService {
    private final StoreRepository storeRepository;
    private final UserSeaerchService userSearchService;
    private final JwtTokenService jwtTokenService;


    public void deleteStore(String accessToken, UUID extStoreId) {
        // 요청 사용자 정보 가져오기
        UserInfoDto userInfoDto = new UserInfoDto(
                jwtTokenService.extractSubject(accessToken)
        );
        User user = userSearchService.searchByInputId(userInfoDto);

        Optional<Store> optionalStore = storeRepository.searchByExtStoreId(extStoreId);
        if (optionalStore.isEmpty()) throw new NoSuchStoreException();
        Store store = optionalStore.get();

        // check if the store's owner is same with request-user
        UUID userId = storeRepository.searchUserByStore(store).orElseThrow(NoSuchStoreException::new);
        //log.debug("userId1:{}, userId2:{}", user.getId(), userId);
        if (userId.compareTo(user.getId()) != 0) throw new InvalidRequestException("DELETE");


        Optional<Address> optionalAddress = storeRepository.searchAddressByStore(store.getId());
        if (optionalAddress.isEmpty()) throw new NoSuchStoreException();
        Address address = optionalAddress.get();

        if (
                store.getExtStoreId().equals(extStoreId)
        ) {
            store.modifyAddress(null);
            address.modifyStore(null);
            storeRepository.delete(store);
        } else throw new InvalidRequestException("DELETE");
    }
}
