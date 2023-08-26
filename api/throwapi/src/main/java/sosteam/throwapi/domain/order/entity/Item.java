package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sosteam.throwapi.domain.order.exception.NotEnoughStockException;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.util.Set;

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
    public void removeStock() {
        int restStock = this.stockQuantity - 1;
        if (restStock < 0) {
            throw new NotEnoughStockException();
        }
        this.stockQuantity = restStock;
    }
}
