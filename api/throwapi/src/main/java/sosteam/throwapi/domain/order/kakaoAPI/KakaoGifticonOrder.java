package sosteam.throwapi.domain.order.kakaoAPI;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import sosteam.throwapi.domain.order.exception.KakaoException;
import sosteam.throwapi.domain.order.kakaoAPI.dto.GifticonSendResponseDto;
import sosteam.throwapi.domain.order.kakaoAPI.dto.GifticonSendRequestDto;

import java.util.Collections;

public class KakaoGifticonOrder {
    private static final String GIFT_SEND_API_URL = "https://gateway-giftbiz.kakao.com/openapi/giftbiz/v1/template/order";
    // TODO: 2023-08-29 인증키를 받아야함
    private static final String AUTHORIZATION_HEADER_VALUE = "KakaoAK abcdefghijk1234567890";

    private RestTemplate restTemplate;

    public KakaoGifticonOrder() {
        this.restTemplate = new RestTemplate();
    }

    public GifticonSendResponseDto sendGift(GifticonSendRequestDto request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", AUTHORIZATION_HEADER_VALUE);

        HttpEntity<GifticonSendRequestDto> entity = new HttpEntity<>(request, headers);
        ResponseEntity<GifticonSendResponseDto> responseEntity = restTemplate.exchange(GIFT_SEND_API_URL, HttpMethod.POST, entity, GifticonSendResponseDto.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new KakaoException(HttpStatus.valueOf(responseEntity.getStatusCode().value()));
        }
    }
}
