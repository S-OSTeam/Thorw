package sosteam.throwapi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.entity.dto.StoreInRadiusDto;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.store.service.StoreCreateService;
import sosteam.throwapi.domain.store.service.StoreSearchService;
import sosteam.throwapi.domain.user.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class StoreSearchServiceTest {
    @InjectMocks
    StoreSearchService storeSearchService;

    @Mock
    StoreRepository storeRepository;

    @Nested
    @DisplayName("가게 검색하기")
    class storeGet {
        @Test
        @DisplayName("내 가게 조회 서비스")
        void myStores() {
            // given
            StoreDto storeDto = new StoreDto(
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
            StoreDto storeDto2 = new StoreDto(
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
            UUID myId = UUID.randomUUID();
            Set<StoreDto> stores = new HashSet<>();
            stores.add(storeDto2);
            stores.add(storeDto);
            given(storeRepository.searchMyStores(myId)).willReturn(Optional.of(stores));

            // when
            Set<StoreDto> result = storeSearchService.searchMyStores(myId);

            // then
            Assertions.assertThat(result.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("이름으로 가게 찾아보기")
        void searchStoreByName() {
            // given
            StoreDto storeDto = new StoreDto(
                    UUID.randomUUID(),
                    "테스트1가게",
                    "01052783309",
                    "0101010101",
                    12.123,
                    127.123,
                    "12341",
                    "테스트 주소",
                    "00000"
            );
            StoreDto storeDto2 = new StoreDto(
                    UUID.randomUUID(),
                    "테스트2가게",
                    "01052783309",
                    "0101010101",
                    12.123,
                    127.123,
                    "12341",
                    "테스트 주소",
                    "00000"
            );
            Set<StoreDto> stores = new HashSet<>();
            stores.add(storeDto);
            stores.add(storeDto2);
            given(storeRepository.searchByName("스트")).willReturn(Optional.of(stores));

            // when
            Set<StoreDto> result = storeSearchService.searchStoreByName("스트");

            // then
            Assertions.assertThat(result.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("사업자 등록번호로 가게 조회")
        void searchStoreByCrn() {
            // given
            String crn = "0101010101";
            StoreDto storeDto = new StoreDto(
                    UUID.randomUUID(),
                    "테스트1가게",
                    "01052783309",
                    crn,
                    12.123,
                    127.123,
                    "12341",
                    "테스트 주소",
                    "00000"
            );
            given(storeRepository.searchByCRN(crn)).willReturn(Optional.of(storeDto));

            // when
            StoreDto result = storeSearchService.searchByCRN(crn);

            // then
            Assertions.assertThat(result.getCrn()).isEqualTo(storeDto.getCrn());
        }

        @Test
        @DisplayName("반경 내 가게들을 검색합니다")
        void searchStoreInRadius() {
            // given
            StoreDto storeDto = new StoreDto(
                    UUID.randomUUID(),
                    "테스트1가게",
                    "01052783309",
                    "0101010101",
                    12.123,
                    127.123,
                    "12341",
                    "테스트 주소",
                    "00000"
            );
            StoreDto storeDto2 = new StoreDto(
                    UUID.randomUUID(),
                    "테스트1가게",
                    "01052783309",
                    "0101010101",
                    12.123,
                    127.123,
                    "12341",
                    "테스트 주소",
                    "00000"
            );
            StoreDto storeDto3 = new StoreDto(
                    UUID.randomUUID(),
                    "테스트1가게",
                    "01052783309",
                    "0101010101",
                    12.123,
                    127.123,
                    "12341",
                    "테스트 주소",
                    "00000"
            );
            StoreInRadiusDto dto = new StoreInRadiusDto(
                    12.123,
                    127.242,
                    4,
                    null
            );
            Set<StoreDto> stores = new HashSet<>();
            stores.add(storeDto);
            stores.add(storeDto2);
            stores.add(storeDto3);
            given(storeRepository.searchStoreInRadius(dto)).willReturn(Optional.of(stores));

            // when
            Set<StoreDto> result = storeSearchService.searchStoreInRadius(dto);

            // then
            Assertions.assertThat(result.size()).isEqualTo(stores.size());

        }
    }
}
