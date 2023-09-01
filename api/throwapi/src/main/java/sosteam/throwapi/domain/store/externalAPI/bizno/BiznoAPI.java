package sosteam.throwapi.domain.store.externalAPI.bizno;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import sosteam.throwapi.domain.store.log.CrnLog;
import sosteam.throwapi.domain.store.log.CrnLogRepository;
import sosteam.throwapi.global.service.IPService;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class BiznoAPI {
    private final CrnLogRepository crnLogRepository;

    @Value("${bizno.api.key}")
    private String apiKey;

    public BiznoApiResponse confirmCompanyRegistrationNumber(String number) {
        // LOG to DB
        CrnLog crnLog = new CrnLog(IPService.getClientIP(), UUID.randomUUID());
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
