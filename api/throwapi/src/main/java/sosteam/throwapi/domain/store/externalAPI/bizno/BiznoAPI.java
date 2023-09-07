package sosteam.throwapi.domain.store.externalAPI.bizno;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import sosteam.throwapi.domain.store.log.CrnLog;
import sosteam.throwapi.domain.store.log.CrnLogRepository;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.UserInfoDto;
import sosteam.throwapi.domain.user.service.UserReadService;
import sosteam.throwapi.global.service.IPService;
import sosteam.throwapi.global.service.JwtTokenService;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class BiznoAPI {
    private final CrnLogRepository crnLogRepository;
    private final UserReadService userReadService;
    private final JwtTokenService jwtTokenService;

    @Value("${bizno.api.key}")
    private String apiKey;

    public BiznoApiResponse confirmCompanyRegistrationNumber(String number,String accessToken) {
        // 요청 사용자 정보 가져오기
        UserInfoDto userInfoDto = new UserInfoDto(
                jwtTokenService.extractSubject(accessToken)
        );
        User user = userReadService.searchByInputId(userInfoDto);

        // LOG to DB
        CrnLog crnLog = new CrnLog(IPService.getClientIP(), user.getId());
        crnLogRepository.save(crnLog);

        log.debug("{},{}", IPService.getClientIP(),number);
        // Call Bizno-api to Confirm CompanyRegistrationNumber
        WebClient webClient = WebClient.builder()
                .baseUrl("https://bizno.net/api/fapi?")
                .build();
        BiznoApiResponse response =
                webClient
                        .get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .queryParam("key",apiKey)
                                        .queryParam("gb",1)
                                        .queryParam("q",number)
                                        .queryParam("type","json")
                                        .build()
                        )
                        .retrieve()
                        .bodyToMono(BiznoApiResponse.class)
                        .block();
        log.debug("BIZNO-API-RESPONSE={}",response);
        log.debug("API-KEY={}", apiKey);
        return response;
    }
}
