package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.entity.Item;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.order.kakaoAPI.KakaoGifticonOrderService;
import sosteam.throwapi.domain.order.kakaoAPI.dto.GifticonSendRequestDto;
import sosteam.throwapi.domain.order.kakaoAPI.dto.GifticonSendResponseDto;
import sosteam.throwapi.domain.order.repository.repo.ReceiptRepository;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.UserInfoDto;
import sosteam.throwapi.domain.user.service.UserInfoService;


import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiptCreateService {
    private final KakaoGifticonOrderService kakaoGifticonOrder;
    private final ReceiptRepository receiptRepository;
    private final UserInfoService userInfoService;

    /**
     * templateToken을 통해 Gifticon과 Receipt 생성
     * @param templateToken
     */
    public Optional<Gifticon> createGifticonAndReceipt(String templateToken, Item item, UserInfoDto userInfoDto) {
        log.debug("SEARCH BY PRODUCT NAME");
        // TODO: 2023-09-04 카카오 템플릿 필요
        GifticonSendResponseDto kakaoResponse = sendKakaoGifticon("123456789");

        // 해당 응답에서 reserveTraceId를 사용하여 Gifticon 엔터티를 생성하고 저장
        User saveUser = userInfoService.searchByInputId(userInfoDto);
        Gifticon saveGifticon = new Gifticon(kakaoResponse.getReserveTraceId().toString());
        saveGifticon.modifyItem(item);
        Receipt saveReceipt=new Receipt();
        saveReceipt.modifyUser(saveUser);
        saveReceipt.modifyGifticon(saveGifticon);
        receiptRepository.save(saveReceipt);

        return Optional.of(saveGifticon);
    }

    /**
     * 템플릿 토큰으로 카카오 기프티콘 생성
     * @param templateToken
     * @return 카카오에서 제공하는 API
     */
    private GifticonSendResponseDto sendKakaoGifticon(String templateToken,User user){
        // TODO: 2023-09-04 토큰 발급 필요
        GifticonSendRequestDto gifticonRequest=new GifticonSendRequestDto(
                templateToken,
                "PHONE",
                Collections.singletonList(new GifticonSendRequestDto.Receiver(user.getUserInfo().getUserPhoneNumber(),user.getUsername())),
                "123.456.789.123/success",
                "123.456.789.123/fail",
                "GIFT",
                "external_order_id"
        );
        return kakaoGifticonOrder.sendGift(gifticonRequest);
    }
}
