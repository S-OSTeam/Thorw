package sosteam.throwapi.domain.store.service;

import org.assertj.core.api.Assertions;
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
import sosteam.throwapi.domain.store.entity.dto.StoreInRadiusDto;
import sosteam.throwapi.domain.store.entity.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.exception.BiznoAPIException;
import sosteam.throwapi.domain.store.exception.InvalidRequestException;
import sosteam.throwapi.domain.store.exception.NoSuchRegistrationNumberException;
import sosteam.throwapi.domain.store.exception.NoSuchStoreException;
import sosteam.throwapi.domain.store.externalAPI.bizno.BiznoAPI;
import sosteam.throwapi.domain.store.externalAPI.bizno.BiznoApiResponse;
import sosteam.throwapi.domain.store.repository.repo.StoreRepository;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.repository.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
    @InjectMocks
    StoreCreateService storeCreateService;
    @InjectMocks
    StoreSearchService storeSearchService;
    @InjectMocks
    StoreDeleteService storeDeleteService;
    @InjectMocks
    StoreModifyService storeModifyService;

    @Mock
    UserRepository userRepository;
    @Mock
    StoreRepository storeRepository;

    @InjectMocks
    BiznoService biznoService;

    @Mock
    BiznoAPI biznoAPI;

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

    @Nested
    @DisplayName("비즈노 API 호출")
    class Bizno {
        @Test
        @DisplayName("비즈노 호출 성공 시 가게 이름을 가져온다")
        void successfulBiznoCall() {
            // given
            List<BiznoApiResponse.Items> items = new ArrayList<>();
            items.add(new BiznoApiResponse.Items("테스트 김밥"));
            BiznoApiResponse response = new BiznoApiResponse(
                    0,
                    "test",
                    1,
                    items
            );
            given(biznoAPI.confirmCompanyRegistrationNumber("1233212343", "test")).willReturn(response);

            //when
            String result = biznoService.confirmCompanyRegistrationNumber("1233212343", "test");

            //then
            Assertions.assertThat(result).isEqualTo("테스트 김밥");
        }

        @Test
        @DisplayName("비즈노 호출 실패 테스트: 사업자 번호 존재 X")
        void unsuccessfulBiznoCall() {
            // 비즈노 호출 시 응답 값이 null 이거나 totalCount가 0이면 예외를 발생한다
            // case 1, case 2 하나 씩 실행해야함 다른거 하나는 주석처리하고 실행하면 된다.

            // given 1
            //given(biznoAPI.confirmCompanyRegistrationNumber("1231212345", "test")).willReturn(null);
            // given 2
            BiznoApiResponse response = new BiznoApiResponse(
                    -9,
                    "test",
                    0,
                    null
            );
            given(biznoAPI.confirmCompanyRegistrationNumber("1231212345", "test")).willReturn(response);
            //when-then
            assertThrows(NoSuchRegistrationNumberException.class, () -> biznoService.confirmCompanyRegistrationNumber("1231212345", "test"));

            //then
            // 예외가 발생해야합니다
            //fail("가게 관련 예외가 발생해야 합니다");
        }
        @Test
        @DisplayName("비즈노 호출 실패 테스트: 비즈노 관련 에러 X")
        void unsuccessfulBiznoCallByBizno() {
            // result code가 -9이므로 비즈노 측 에러 발생
            // given
            BiznoApiResponse response = new BiznoApiResponse(
                    -9,
                    "test",
                    1,
                    null
            );
            given(biznoAPI.confirmCompanyRegistrationNumber("1231212345", "test")).willReturn(response);
            //when-then
            assertThrows(BiznoAPIException.class, () -> biznoService.confirmCompanyRegistrationNumber("1231212345", "test"));

            //then
            // 예외가 발생해야합니다
            //fail("가게 관련 예외가 발생해야 합니다");
        }
    }
}