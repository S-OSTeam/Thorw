package sosteam.throwapi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sosteam.throwapi.domain.store.exception.BiznoAPIException;
import sosteam.throwapi.domain.store.exception.NoSuchRegistrationNumberException;
import sosteam.throwapi.domain.store.externalAPI.bizno.BiznoAPI;
import sosteam.throwapi.domain.store.externalAPI.bizno.BiznoApiResponse;
import sosteam.throwapi.domain.store.service.BiznoService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
public class BiznoServiceTest {

    @InjectMocks
    BiznoService biznoService;

    @Mock
    BiznoAPI biznoAPI;

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
