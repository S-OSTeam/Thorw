package sosteam.throwapi.domain.order.controller.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import sosteam.throwapi.domain.order.entity.Item;

import java.util.UUID;

@Data
public class ItemResponse {
    @NotNull
    UUID itemId;

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

}
