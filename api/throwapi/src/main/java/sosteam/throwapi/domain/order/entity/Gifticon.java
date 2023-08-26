package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.util.UUID;

@Getter
@Entity
public class Gifticon extends PrimaryKeyEntity {
    @NotNull
    private String couponNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    @Enumerated(EnumType.STRING)
    private GifticonStatus gifticonStatus; // 주문 상태 [WAIT,SOLD]

    public void modifyReceipt(Receipt receipt) {
        this.receipt=receipt;
    }

    public void modifyItem(Item item){
        this.item=item;
    }

    //==생성 메서드==//
    public static Gifticon createGifticon(Item item,int count) {
        Gifticon gifticon=new Gifticon();
        gifticon.modifyItem(item);
        // 여기 수정해야됨.
        item.addStock();
        return gifticon;
    }
}
