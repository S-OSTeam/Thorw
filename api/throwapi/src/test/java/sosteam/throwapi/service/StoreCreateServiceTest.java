package sosteam.throwapi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.entity.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.store.service.StoreCreateService;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreCreateServiceTest {
    @InjectMocks
    StoreCreateService storeCreateService;

    @Mock
    UserRepository userRepository;
    @Mock
    StoreRepository storeRepository;
    @Nested
    @DisplayName("가게 저장하기")
    class storeCreate{
        @Test
        @DisplayName("새로운 가게를 저장합니다")
        void saveStore() {
            // given
            User user = new User();
            StoreSaveDto storeDto = new StoreSaveDto(
                    UUID.randomUUID(),
                    "테스트가게",
                    "01052783309",
                    "0101010101",
                    12.123,
                    127.123,
                    "12341",
                    "테스트 주소",
                    "00000"
            );
            Store store = new Store(
                    UUID.randomUUID(),
                    "테스트가게",
                    "01052783309",
                    "0101010101",
                    "00000"
            );
            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            doReturn(store).when(storeRepository).save(any(Store.class));
            //given(storeRepository.save(store)).willReturn(store);

            //when
            storeCreateService.saveStore(user.getId(),storeDto);

            // then
            verify(storeRepository).save(any());
        }
    }
}
