package sosteam.throwapi.domain.store.externalAPI.bizno;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class BiznoAPI {

    @Value("${bizno.api.key}")
    private String apiKey;

    public BiznoApiResponse confirmCompanyRegistrationNumber(String number) {
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
