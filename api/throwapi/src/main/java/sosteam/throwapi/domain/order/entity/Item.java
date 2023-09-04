package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends PrimaryKeyEntity {

    @NotNull
    private String templateToken; // 템플릿 토큰

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

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private Set<Gifticon> gifticons;

    public Item(String templateToken, String productName, String brandName, String brandImageUrl, String productImageUrl, String productThumbImageUrl, Long price) {
        this.templateToken = templateToken;
        this.productName = productName;
        this.brandName = brandName;
        this.brandImageUrl = brandImageUrl;
        this.productImageUrl = productImageUrl;
        this.productThumbImageUrl = productThumbImageUrl;
        this.price = price;
        this.gifticons=new HashSet<>();
    }

    public void addGifticon(Gifticon gifticon){
        gifticons.add(gifticon);
        gifticon.modifyItem(this);
    }
}
