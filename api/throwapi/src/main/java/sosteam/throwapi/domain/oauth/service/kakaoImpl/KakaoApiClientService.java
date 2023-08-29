package sosteam.throwapi.domain.oauth.service.kakaoImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import sosteam.throwapi.domain.oauth.service.OAuthApiClientService;
import sosteam.throwapi.domain.oauth.service.OAuthLoginParamsService;
import sosteam.throwapi.global.entity.SNSCategory;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoApiClientService implements OAuthApiClientService {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.kakao.url.auth}")
    private String authUrl;

    @Value("${oauth.kakao.url.api}")
    private String apiUrl;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.url.redirect}")
    private String redirectURI;


    @Override
    public SNSCategory oAuthProvider() {
        return SNSCategory.KAKAO;
    }

    @Override
    public String reissueAccessToken(String refreshToken){
        log.debug("in reissueAccessToken");
        log.debug("refreshToken = {}", refreshToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("client_id", clientId);
        body.add("refresh_token", refreshToken);

        for (String s : body.keySet()) {
            log.debug("AccessToken body key {}", s);
            log.debug("AccessToken body value {}", body.get(s));
        }

        WebClient webClient = WebClient.builder()
                .baseUrl(authUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();


        Map<String, Object> response =
                webClient.post()
                        .uri("/oauth/token")
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();
        log.debug("response = {}", response);
        assert response != null;
        return response.get("access_token").toString();
    }

    @Override
    public String requestOAuthId(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);


        WebClient webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        Map<String, Object> response =
                webClient.get()
                        .uri("/v2/user/me")
                        .header("Authorization", "Bearer " + accessToken)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();
        return response.get("id").toString();
    }

    @Override
    public boolean requestTokenValidation(String accessToken){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        WebClient webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
        try{
            Map<String, Object> response =
                    webClient.get()
                            .uri("/v1/user/access_token_info")
                            .header("Authorization", "Bearer " + accessToken)
                            .retrieve()
                            .bodyToMono(Map.class)
                            .block();
        } catch (Exception e){
            log.error("fail to request kakao token info = {}", e.getMessage());
            return false;
        }
        return true;
    }


}
