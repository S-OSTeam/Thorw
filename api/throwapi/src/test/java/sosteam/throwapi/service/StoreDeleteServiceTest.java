package sosteam.throwapi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sosteam.throwapi.domain.store.entity.Address;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.exception.InvalidRequestException;
import sosteam.throwapi.domain.store.exception.NoSuchStoreException;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.store.service.StoreDeleteService;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreDeleteServiceTest {
    @InjectMocks
    StoreDeleteService storeDeleteService;
    @Mock
    UserRepository userRepository;
    @Mock
    StoreRepository storeRepository;

    @Nested
    @DisplayName("가게 삭제하기")
    class storeDelete {
        //given
        User user = new User();
        UUID ext = UUID.randomUUID();
        Address address = new Address(
                null,
                12,
                127,
                "qwe",
                "10298"
        );
        Store store = new Store(
                ext,
                "테스트가게",
                "01052783309",
                "0101010101",
                "00000"
        );
        @Test
        @DisplayName("가게를 삭제합니당")
        void storeDeleteTest() {
            // given
            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            given(storeRepository.searchByExtStoreId(ext)).willReturn(Optional.of(store));
            given(storeRepository.searchUserByStore(store)).willReturn(Optional.of(user.getId()));

            // when
            storeDeleteService.deleteStore(user.getId(), ext);

            // then
            verify(storeRepository).delete(any());
        }

        @Test
        @DisplayName("예외 : NO_SUCH_STORE_EXCEPTION")
        void storeDoNotExistException() {
            // given
            //when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            //given(storeRepository.searchByExtStoreId(ext)).willReturn(Optional.of(store));
            //given(storeRepository.searchUserByStore(store)).willReturn(Optional.of(user.getId()));
            // when
            assertThrows(NoSuchStoreException.class, () -> storeDeleteService.deleteStore(user.getId(), ext));
            // then
            //fail("가게 관련 예외가 발생해야 합니다");
        }

        @Test
        @DisplayName("예외 : INVALID_REQUEST")
        void inValidRequestStoreDelete() {
            // given
            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            given(storeRepository.searchByExtStoreId(ext)).willReturn(Optional.of(store));
            //given(storeRepository.searchUserByStore(store)).willReturn(Optional.of(user.getId()));
            //given(storeRepository.searchAddressByStore(store.getId())).willReturn(Optional.of(address));
            // when
            assertThrows(InvalidRequestException.class, () -> storeDeleteService.deleteStore(user.getId(), ext));
            // then
            //fail("가게 관련 예외가 발생해야 합니다");
        }
    }
}
