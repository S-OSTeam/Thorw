package sosteam.throwapi.domain.order.controller.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class GifticonResponse {
    @NotNull
    private String productName; // 테스트상품명

    @NotNull
    private String brandName; // 테스트브랜드명

    @NotNull
    private String brandImageUrl; // 테스트브랜드이미지url

    @NotNull
    private String productImageUrl; // 테스트상품이미지url

    @NotNull
    private  String productThumbImageUrl; // 테스트상품썸네일url

    @NotNull
    private Long price; // 상품 가격

    @NotNull
    UUID gifticonId; // gifticon 엔티티 ID

    @NotNull
    private String giftTraceId; // 주문 완료 후 생성되는 수신자 단위의 선물 번호
}
