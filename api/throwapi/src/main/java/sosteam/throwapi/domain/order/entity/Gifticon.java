package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gifticon extends PrimaryKeyEntity {

    @NotNull
    private String giftTraceId; // 주문 완료 후 생성되는 수신자 단위의 선물 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id",nullable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    public Gifticon(String giftTraceId) {
        this.giftTraceId = giftTraceId;
    }

    public void modifyGiftTraceId(String giftTraceId) {
        this.giftTraceId = giftTraceId;
    }

    public void modifyReceipt(Receipt receipt) {
        this.receipt=receipt;
    }
}
