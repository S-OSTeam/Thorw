package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.CascadeType;
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

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gifticon> gifticons;
}
