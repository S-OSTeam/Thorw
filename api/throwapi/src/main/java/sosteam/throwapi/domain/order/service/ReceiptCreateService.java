package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.order.kakaoAPI.KakaoGifticonOrderService;
import sosteam.throwapi.domain.order.kakaoAPI.dto.GifticonSendRequestDto;
import sosteam.throwapi.domain.order.kakaoAPI.dto.GifticonSendResponseDto;
import sosteam.throwapi.domain.order.repository.repo.ReceiptRepository;
import sosteam.throwapi.domain.user.entity.User;


import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiptCreateService {
    private final KakaoGifticonOrderService kakaoGifticonOrder;
    private final ReceiptRepository receiptRepository;

    /**
     * templateToken을 통해 Gifticon과 Receipt 생성
     * @param templateToken
     */
    public Optional<Gifticon> createGifticonAndReceipt(String templateToken) {
        log.debug("SEARCH BY PRODUCT NAME");
        // TODO: 2023-08-29 템플릿 변경, user 정보 추가(수형이 코드 필요)
//        GifticonSendResponseDto kakaoResponse = sendKakaoGifticon("123456789");

        //-----------------test 코드-----------------
        GifticonSendResponseDto.TemplateReceiver receiver1 = new GifticonSendResponseDto.TemplateReceiver(
                1, // sequence
                "externalKey1", // externalKey
                "Name1", // name
                "ReceiverId1" // receiverId
        );

        GifticonSendResponseDto.TemplateReceiver receiver2 = new GifticonSendResponseDto.TemplateReceiver(
                2, // sequence
                "externalKey2", // externalKey
                "Name2", // name
                "ReceiverId2" // receiverId
        );

        // GifticonSendResponseDto 예시 객체 생성
        GifticonSendResponseDto kakaoResponse = new GifticonSendResponseDto(
                123456L, // reserveTraceId
                Arrays.asList(receiver1, receiver2) // templateReceivers
        );
        //-----------------test 코드-----------------

        // 해당 응답에서 reserveTraceId를 사용하여 Gifticon 엔터티를 생성하고 저장
        // TODO: 2023-09-01 User 추가
        User saveUser=new User();
        Gifticon saveGifticon = new Gifticon(kakaoResponse.getReserveTraceId().toString());
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
    private GifticonSendResponseDto sendKakaoGifticon(String templateToken){
        // TODO: 2023-08-29 기프티콘 정보(수형이 코드 필요)
        GifticonSendRequestDto gifticonRequest=new GifticonSendRequestDto(
                templateToken,
                "PHONE",
//                Collections.singletonList(new GifticonSendRequestDto.Receiver(userRepository.searchPhoneNumber,userRepository.searchName)),
                Collections.singletonList(new GifticonSendRequestDto.Receiver("01012345678", "홍길동")),
                "123.456.789.123/success",
                "123.456.789.123/fail",
                "GIFT",
                "external_order_id"
        );
        return kakaoGifticonOrder.sendGift(gifticonRequest);
    }
}
