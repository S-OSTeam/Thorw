package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sosteam.throwapi.domain.order.exception.NotEnoughStockException;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.util.Set;
import java.util.UUID;

@Getter
@Entity
public class Item extends PrimaryKeyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;

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
    public void addStock(int quantity) {
        this.stockQuantity+=quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock=this.stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }
}
