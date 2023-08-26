package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Getter
@Entity
public class Gifticon extends PrimaryKeyEntity {

    @NotNull
    private String gift_trace_id; // 주문 완료 후 생성되는 수신자 단위의 선물 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;
}
