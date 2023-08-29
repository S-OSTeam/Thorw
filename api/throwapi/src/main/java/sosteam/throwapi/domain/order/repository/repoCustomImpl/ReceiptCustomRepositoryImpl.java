package sosteam.throwapi.domain.order.repository.repoCustomImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sosteam.throwapi.domain.order.entity.*;
import sosteam.throwapi.domain.order.kakaoAPI.KakaoGifticonOrder;
import sosteam.throwapi.domain.order.kakaoAPI.dto.GifticonSendRequestDto;
import sosteam.throwapi.domain.order.kakaoAPI.dto.GifticonSendResponseDto;
import sosteam.throwapi.domain.order.repository.repo.GifticonRepository;
import sosteam.throwapi.domain.order.repository.repo.ReceiptRepository;
import sosteam.throwapi.domain.order.repository.repoCustom.ReceiptCustomRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static sosteam.throwapi.domain.order.entity.QGifticon.gifticon;
import static sosteam.throwapi.domain.order.entity.QReceipt.receipt;

@Repository
@RequiredArgsConstructor
public class ReceiptCustomRepositoryImpl implements ReceiptCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final UserRepository userRepository; // TODO: 2023-08-29 (수형이 코드 필요)

    private KakaoGifticonOrder kakaoGifticonOrder;

    @Override
    public Optional<Set<Receipt>> searchByUserId(UUID userId) {
        return Optional.ofNullable(new HashSet<>(jpaQueryFactory.selectFrom(receipt)
                .where(receipt.user.id.eq(userId))
                .fetch()));
    }

    @Override
    public Optional<Gifticon> createGifticonAndReceipt(String templateToken) {
        // TODO: 2023-08-29 템플릿 변경, user 정보 추가(수형이 코드 필요)
        GifticonSendResponseDto kakaoResponse = sendKakaoGifticon("123456789");

        // 해당 응답에서 reserveTraceId를 사용하여 Gifticon 엔터티를 생성하고 저장
        jpaQueryFactory.insert(gifticon)
                .columns(gifticon.giftTraceId)
                .values(kakaoResponse.getReserveTraceId())
                .execute();

        Gifticon insertedGifticon = jpaQueryFactory.selectFrom(gifticon)
                .where(gifticon.giftTraceId.eq(kakaoResponse.getReserveTraceId().toString()))
                .orderBy(gifticon.id.desc())
                .fetchFirst();

        // Receipt 엔터티를 생성하고 저장
        jpaQueryFactory.insert(receipt)
                .columns(receipt.gifticon, receipt.receiptStatus)
                .values(insertedGifticon, ReceiptStatus.SALE)
                .execute();

        return Optional.ofNullable(insertedGifticon);
    }

    private GifticonSendResponseDto sendKakaoGifticon(String templateToken){
        // TODO: 2023-08-29 기프티콘 정보(수형이 코드 필요)
        GifticonSendRequestDto gifticonRequest=new GifticonSendRequestDto(
                templateToken,
                "PHONE",
                GifticonSendRequestDto.Receiver(userRepository.searchPhoneNumber,userRepository.searchName),
                "123.456.789.123/success",
                "123.456.789.123/fail",
                "GIFT",
                "external_order_id"
        );
        return kakaoGifticonOrder.sendGift(gifticonRequest);
    }
}
