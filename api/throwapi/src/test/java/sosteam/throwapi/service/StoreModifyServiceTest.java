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
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.exception.InvalidRequestException;
import sosteam.throwapi.domain.store.exception.NoSuchStoreException;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.store.service.StoreModifyService;
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
public class StoreModifyServiceTest {

    @InjectMocks
    StoreModifyService storeModifyService;
    @Mock
    UserRepository userRepository;
    @Mock
    StoreRepository storeRepository;

    @Nested
    @DisplayName("가게 수정하기")
    class storeModify {
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
        StoreDto dto = new StoreDto(
                ext,
                "테스트가게변경하자",
                "01012341234",
                "1111111111",
                13.1,
                129.1,
                "01232",
                "10298",
                "10111"
        );

        @Test
        @DisplayName("가게를 수정합니당")
        void storeModifyTest() {
            // given
            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            given(storeRepository.searchByExtStoreId(ext)).willReturn(Optional.of(store));
            given(storeRepository.searchAddressByStore(store.getId())).willReturn(Optional.of(address));
            given(storeRepository.searchUserByStore(store)).willReturn(Optional.of(user.getId()));
            // when
            storeModifyService.modify(user.getId(), dto);

            // then
            verify(storeRepository).save(any());
        }

        @Test
        @DisplayName("예외 : NO_SUCH_STORE_EXCEPTION")
        void storeDoNotExistException() {
            // given
            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            given(storeRepository.searchByExtStoreId(ext)).willReturn(Optional.of(store));
            given(storeRepository.searchUserByStore(store)).willReturn(Optional.of(user.getId()));
            // 아래 코드가 없으면 NoSuchStore가 터진다.
            //given(storeRepository.searchAddressByStore(store.getId())).willReturn(Optional.of(address));
            // when - then
            assertThrows(NoSuchStoreException.class, () -> storeModifyService.modify(user.getId(), dto));
            // then
            //fail("가게 관련 예외가 발생해야 합니다");
        }

        @Test
        @DisplayName("예외 : INVALID_REQUEST")
        void inValidRequestStoreDelete() {
            // given
            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            given(storeRepository.searchByExtStoreId(ext)).willReturn(Optional.of(store));
            // 아래 코드가 없으면 InvalidRequest가 터진다.
            //given(storeRepository.searchUserByStore(store)).willReturn(Optional.of(user.getId()));
            // when
            assertThrows(InvalidRequestException.class, () -> storeModifyService.modify(user.getId(), dto));
            // then
            //fail("가게 관련 예외가 발생해야 합니다");
        }
    }
}
