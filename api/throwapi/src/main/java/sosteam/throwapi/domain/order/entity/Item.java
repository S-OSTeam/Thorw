package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.util.List;

@Getter
@Entity
public class Item extends PrimaryKeyEntity {

    @NotNull
    private String product_name; // 테스트상품명

    @NotNull
    private String brand_name; // 테스트브랜드명

    @NotNull
    private String brand_image_url; // 테스트브랜드이미지url

    @NotNull
    private String product_image_url; // 테스트상품이미지url

    @NotNull
    private  String product_thumb_image_url; // 테스트상품썸네일url

    @NotNull
    private Long price; // 상품 가격

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Gifticon> gifticons;
}
