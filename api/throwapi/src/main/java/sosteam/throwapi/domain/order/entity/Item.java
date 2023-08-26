package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sosteam.throwapi.domain.order.exception.NotEnoughStockException;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.util.Set;
import java.util.UUID;

@Getter
@Entity
public class Item extends PrimaryKeyEntity {
    @NotNull
    private String name;

    @NotNull
    private Long price;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private Set<Gifticon> gifticons;

    private int stockQuantity;

    //==비즈니스 로직==//
    /**
     * stock 증가
     */
    public void addStock() {
        this.stockQuantity++;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock=this.stockQuantity-1;
        if(restStock<0){
            throw new NotEnoughStockException("재고가 부족합니다", HttpStatus.CONFLICT);
        }
        this.stockQuantity=restStock;
    }
}
